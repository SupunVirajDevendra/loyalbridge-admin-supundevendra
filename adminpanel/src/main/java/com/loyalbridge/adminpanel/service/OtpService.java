package com.loyalbridge.adminpanel.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class OtpService {
    private final Map<String, OtpData> otpStore = new ConcurrentHashMap<>();
    private static final int OTP_LENGTH = 6;
    private static final int OTP_VALIDITY_MINUTES = 5;

    public String generateOtp(String email) {
        String otp = generateRandomOtp();
        otpStore.put(email, new OtpData(otp, LocalDateTime.now()));
        log.info("Generated OTP for email: {} - OTP: {}", email, otp);
        return otp;
    }

    public boolean validateOtp(String email, String otp) {
        log.info("Validating OTP for email: {} - Provided OTP: {}", email, otp);
        OtpData otpData = otpStore.get(email);
        if (otpData == null) {
            log.warn("No OTP found for email: {}", email);
            return false;
        }

        log.info("Stored OTP: {} - Generated at: {}", otpData.otp, otpData.generatedAt);
        boolean isValid = otpData.otp.equals(otp) && 
                         !otpData.generatedAt.plusMinutes(OTP_VALIDITY_MINUTES).isBefore(LocalDateTime.now());
        
        if (isValid) {
            log.info("OTP validation successful for email: {}", email);
            otpStore.remove(email);
        } else {
            log.warn("OTP validation failed for email: {} - OTP expired or invalid", email);
        }
        
        return isValid;
    }

    private String generateRandomOtp() {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }

    private static class OtpData {
        final String otp;
        final LocalDateTime generatedAt;

        OtpData(String otp, LocalDateTime generatedAt) {
            this.otp = otp;
            this.generatedAt = generatedAt;
        }
    }
} 