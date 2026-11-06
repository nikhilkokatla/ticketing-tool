
package com.ticketingtool.auth;

import com.ticketingtool.auth.dto.RegisterRequest;
import com.ticketingtool.auth.dto.LoginRequest;
import com.ticketingtool.auth.dto.AuthResponse;
import com.ticketingtool.user.User;
import com.ticketingtool.user.UserRepository;
import com.ticketingtool.user.UserRole;
import com.ticketingtool.security.JwtService;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // User Registration
    public String register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        user.setPasswordHash(
                passwordEncoder.encode(request.getPassword())
        );

        // Assign CUSTOMER role by default
        user.setRole(UserRole.CUSTOMER);

        userRepository.save(user);

        return "User registered successfully";
    }

    // User Login
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPasswordHash())) {

            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        return new AuthResponse(
                "Login successful",
                token,
                "Bearer",
                user.getRole().name()
        );
    }
}
