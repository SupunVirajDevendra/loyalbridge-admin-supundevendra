package com.loyalbridge.adminpanel.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    private String email;
    private String password;
    private String otp; // Optional OTP for 2FA
}
