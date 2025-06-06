package com.loyalbridge.adminpanel.service;

import com.loyalbridge.adminpanel.dto.*;
import com.loyalbridge.adminpanel.model.User;
import com.loyalbridge.adminpanel.repository.UserRepository;
import com.loyalbridge.adminpanel.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest request) {
        if (!request.getEmail().endsWith("@loyalbridge.io")) {
            throw new RuntimeException("Invalid email domain");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        userRepository.save(user);
        return new AuthResponse(jwtUtil.generateToken(user));
    }

    public AuthResponse login(LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return new AuthResponse(jwtUtil.generateToken(user));
            }
        }
        throw new RuntimeException("Invalid credentials");
    }
}