package com.brihaspathee.book.config;

import com.brihaspathee.book.auth.repository.UserRepository;
import com.brihaspathee.book.auth.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 2/12/25
 * Time: 7:35 PM
 * Project: book-social-network
 * Package Name: com.brihaspathee.book.config
 * To change this template use File | Settings | File and Code Template
 */
@Service
@RequiredArgsConstructor
public class BSNUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return user;
    }
}
