package com.brihaspathee.book.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 2/12/25
 * Time: 7:15 PM
 * Project: book-social-network
 * Package Name: com.brihaspathee.book.config
 * To change this template use File | Settings | File and Code Template
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * Defines a list of endpoint patterns that are whitelisted for bypassing
     * authentication checks in the security configuration. These endpoints are
     * generally used for accessing public resources or API documentation, such
     * as Swagger UI and OpenAPI specifications.
     *
     * It includes:
     * - Swagger UI and OpenAPI documentation endpoints.
     * - Other public API endpoints, such as authentication endpoints, that do not
     *   require secured access.
     *
     * This list is used in the security configuration to configure certain
     * requests to be publicly accessible while enforcing authentication for other
     * endpoints.
     */
    private static final String[] AUTH_WHITELIST = {


            // -- Swagger UI v3 (OpenAPI)
            "/swagger-resources",
            "/swagger-resources/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/configuration/ui",
            "/confiuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html",
            "/v3/api-docs.yaml",
            // other public endpoints of your API may be appended to this array
            "/auth/**"
    };

    private final AuthenticationProvider authenticationProvider;

    private final JwtFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request.requestMatchers(AUTH_WHITELIST).permitAll()
                                .anyRequest().authenticated())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
