package com.example.CenterManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for testing
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/users/**").permitAll() // Allow all user endpoints
                        .anyRequest().authenticated() // Secure other endpoints
                )
                .formLogin(form -> form.disable()) // Disable login form
                .httpBasic(basic -> basic.disable()); // Disable basic auth

        return http.build();
    }
}
