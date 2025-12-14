package com.rainstream.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for simpler API testing
                .authorizeHttpRequests(auth -> auth
                        // Allow access to auth endpoints (we will create these next)
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        // Any other request requires the user to be logged in
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}