package com.loyalbridge.adminpanel.service;

import com.loyalbridge.adminpanel.dto.*;
import com.loyalbridge.adminpanel.model.User;
import com.loyalbridge.adminpanel.repository.UserRepository;
import com.loyalbridge.adminpanel.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final OtpService otpService;

    private static final Pattern PASSWORD_PATTERN = 
        Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{12,}$");

    public AuthResponse register(RegisterRequest request) {
        log.info("Processing registration for email: {}", request.getEmail());
        if (!request.getEmail().endsWith("@loyalbridge.io")) {
            throw new RuntimeException("Invalid email domain");
        }

        if (!PASSWORD_PATTERN.matcher(request.getPassword()).matches()) {
            throw new RuntimeException("Password must be at least 12 characters long and contain at least one uppercase letter, one special character, and one number");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setName(request.getFirstName() + " " + request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setRiskLevel(request.getRiskLevel());
        user.setStatus(request.getStatus());
        user.setActive(request.getStatus() == User.Status.ACTIVE);
        user.setFrozen(request.isFrozen());
        user.setVerified(request.isVerified());
        user.setIsVerified(request.isVerified());
        user.setHighRisk(request.getRiskLevel() == User.RiskLevel.HIGH);
        userRepository.save(user);

        log.info("Registration successful for email: {}", request.getEmail());
        return new AuthResponse(jwtUtil.generateToken(user), false, "Registration successful");
    }

    public AuthResponse login(LoginRequest request) {
        log.info("Processing login for email: {}", request.getEmail());
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isEmpty()) {
            log.warn("Login failed - User not found: {}", request.getEmail());
            throw new RuntimeException("Invalid credentials");
        }

        User user = userOpt.get();
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.warn("Login failed - Invalid password for user: {}", request.getEmail());
            throw new RuntimeException("Invalid credentials");
        }

        // Always require OTP if not provided
        if (request.getOtp() == null) {
            log.info("OTP required for user: {}", request.getEmail());
            String otp = otpService.generateOtp(request.getEmail());
            return new AuthResponse(null, true, "OTP required. For testing, use OTP: " + otp);
        }

        // Validate OTP if provided
        log.info("Validating OTP for user: {}", request.getEmail());
        if (!otpService.validateOtp(request.getEmail(), request.getOtp())) {
            log.warn("Invalid OTP provided for user: {}", request.getEmail());
            throw new RuntimeException("Invalid OTP");
        }
        log.info("OTP validation successful for user: {}", request.getEmail());

        String token = jwtUtil.generateToken(user);
        log.info("Login successful for user: {}", request.getEmail());
        return new AuthResponse(token, false, "Login successful");
    }
}