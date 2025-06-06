package com.loyalbridge.adminpanel.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RiskLevel riskLevel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(name = "is_active", nullable = false, columnDefinition = "boolean default false")
    private boolean isActive;

    @Column(name = "is_frozen", nullable = false, columnDefinition = "boolean default false")
    private boolean frozen;

    @Column(name = "is_verified", nullable = false, columnDefinition = "boolean default false")
    private boolean isVerified;

    @Column(name = "verified", nullable = false, columnDefinition = "boolean default false")
    private boolean verified;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isHighRisk;

    private Integer totalPoints = 0;
    private Integer availablePoints = 0;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime lastLoginAt;

    public enum Role {
        SUPER_ADMIN,
        FINANCE_TEAM,
        SUPPORT_STAFF,
        PARTNER_ADMIN
    }

    public enum RiskLevel {
        LOW,
        MEDIUM,
        HIGH
    }

    public enum Status {
        ACTIVE,
        INACTIVE,
        SUSPENDED
    }

    public boolean isActive() {
        return status == Status.ACTIVE;
    }

    public boolean isHighRisk() {
        return riskLevel == RiskLevel.HIGH;
    }

    public void setStatus(Status status) {
        this.status = status;
        this.isActive = (status == Status.ACTIVE);
    }

    public void setActive(boolean active) {
        this.isActive = active;
        this.status = active ? Status.ACTIVE : Status.INACTIVE;
    }

    public void setHighRisk(boolean highRisk) {
        this.isHighRisk = highRisk;
        this.riskLevel = highRisk ? RiskLevel.HIGH : RiskLevel.LOW;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
        this.isVerified = verified;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
        this.verified = isVerified;
    }

    public boolean getIsVerified() {
        return isVerified;
    }
}
