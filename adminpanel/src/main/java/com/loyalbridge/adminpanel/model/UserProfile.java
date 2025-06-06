package com.loyalbridge.adminpanel.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String company;
    private String position;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    private String verificationDocument;
    private String verificationStatus;
} 