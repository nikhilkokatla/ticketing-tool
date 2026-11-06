
package com.ticketingtool.config;

import com.ticketingtool.security.JwtAuthenticationFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    // CORS configuration
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(
                Arrays.asList("http://localhost:4200")
        );

        configuration.setAllowedMethods(
                Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")
        );

        configuration.setAllowedHeaders(
                Arrays.asList(
                        HttpHeaders.AUTHORIZATION,
                        HttpHeaders.CONTENT_TYPE
                )
        );

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                // Enable CORS
                .cors(cors ->
                        cors.configurationSource(corsConfigurationSource())
                )

                // Disable CSRF
                .csrf(csrf -> csrf.disable())

                // Configure API authorization
                .authorizeHttpRequests(auth -> auth

                        // Allow CORS preflight requests
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Authentication APIs
                        .requestMatchers("/api/auth/**").permitAll()

                        // Customer Dashboard
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/dashboard/customer"
                        ).hasRole("CUSTOMER")

                        // Agent Dashboard
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/dashboard/statistics"
                        ).hasRole("AGENT")

                        // Customer ticket APIs
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/tickets/my-tickets"
                        ).hasRole("CUSTOMER")

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/tickets"
                        ).hasRole("CUSTOMER")

                        // Agent ticket APIs
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/tickets"
                        ).hasRole("AGENT")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/tickets/*/assign/*"
                        ).hasRole("AGENT")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/tickets/*"
                        ).hasRole("AGENT")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/tickets/*"
                        ).hasRole("AGENT")

                        // Individual ticket access
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/tickets/*"
                        ).authenticated()

                        // All remaining APIs require authentication
                        .anyRequest().authenticated()
                )

                // Stateless JWT authentication
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Add JWT filter
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    // BCrypt password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Authentication manager
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }
}
