package com.loyalbridge.adminpanel.dto;

import com.loyalbridge.adminpanel.model.User;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private User.Role role;
    private User.RiskLevel riskLevel;
    private User.Status status;
    private boolean isFrozen;
    private boolean isVerified;
}

