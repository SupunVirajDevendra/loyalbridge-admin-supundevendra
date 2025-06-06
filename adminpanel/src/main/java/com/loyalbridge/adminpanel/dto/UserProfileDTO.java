package com.loyalbridge.adminpanel.dto;

import com.loyalbridge.adminpanel.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserProfileDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String status;
    private boolean isFrozen;
    private boolean isVerified;
    private String riskLevel;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
    private Integer totalPoints;
    private Integer availablePoints;

    public static UserProfileDTO fromUser(User user) {
        UserProfileDTO dto = new UserProfileDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setStatus(user.getStatus().name());
        dto.setFrozen(user.isFrozen());
        dto.setVerified(user.isVerified());
        dto.setRiskLevel(user.getRiskLevel().name());
        dto.setRole(user.getRole().name());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setLastLoginAt(user.getLastLoginAt());
        dto.setTotalPoints(user.getTotalPoints());
        dto.setAvailablePoints(user.getAvailablePoints());
        return dto;
    }
} 