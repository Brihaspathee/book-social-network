package com.brihaspathee.book.service.impl;

import com.brihaspathee.book.auth.entity.Role;
import com.brihaspathee.book.auth.entity.Token;
import com.brihaspathee.book.auth.entity.User;
import com.brihaspathee.book.auth.repository.RoleRepository;
import com.brihaspathee.book.auth.repository.TokenRepository;
import com.brihaspathee.book.auth.repository.UserRepository;
import com.brihaspathee.book.constants.EmailTemplateName;
import com.brihaspathee.book.models.RegistrationRequest;
import com.brihaspathee.book.service.interfaces.AuthenticationService;
import com.brihaspathee.book.service.interfaces.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 2/19/25
 * Time: 6:17 PM
 * Project: book-social-network
 * Package Name: com.brihaspathee.book.service.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;
    private final EmailService emailService;

    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

    @Override
    public void register(RegistrationRequest registrationRequest) throws MessagingException {
        Role userRole = roleRepository.findByRoleName("USER")
                // todo better exception handling
                .orElseThrow(() -> new IllegalStateException("Role User not found"));
        User user = User.builder()
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();
        log.info("Registering user: {}", user);
        userRepository.save(user);
        log.info("Registered user: {}", user);
        sendValidationEmail(user);
        log.info("Email Sent");
    }

    private void sendValidationEmail(User user) throws MessagingException {
        String newToken = generateAndSaveActivationToken(user);
        log.info("New activation token: {}", newToken);
        log.info("New activation email: {}", user.getEmail());
        log.info("Email is to be sent");
        emailService.sendEmail(
                user.getEmail(),
                user.getFullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account Activation"
        );
    }

    private String generateAndSaveActivationToken(User user) {
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder activationCodeBuilder = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            activationCodeBuilder.append(characters.charAt(randomIndex));
        }
        return activationCodeBuilder.toString();
    }
}
