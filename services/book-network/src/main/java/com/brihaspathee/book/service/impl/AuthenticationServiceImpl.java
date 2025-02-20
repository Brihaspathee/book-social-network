package com.brihaspathee.book.service.impl;

import com.brihaspathee.book.auth.entity.Role;
import com.brihaspathee.book.auth.entity.Token;
import com.brihaspathee.book.auth.entity.User;
import com.brihaspathee.book.auth.repository.RoleRepository;
import com.brihaspathee.book.auth.repository.TokenRepository;
import com.brihaspathee.book.auth.repository.UserRepository;
import com.brihaspathee.book.models.RegistrationRequest;
import com.brihaspathee.book.service.interfaces.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public void register(RegistrationRequest registrationRequest) {
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
        userRepository.save(user);
        sendValidationEmail(user);
    }

    private void sendValidationEmail(User user) {
        String newToken = generateAndSaveActivationToken(user);
        // todo send email
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
