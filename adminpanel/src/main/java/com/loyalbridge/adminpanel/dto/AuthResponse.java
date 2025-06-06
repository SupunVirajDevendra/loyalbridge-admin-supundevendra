package com.loyalbridge.adminpanel.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private boolean requiresOtp;
    private String message;
}
