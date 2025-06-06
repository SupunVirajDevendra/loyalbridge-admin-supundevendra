package com.loyalbridge.adminpanel.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "partners")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Partner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String apiUrl;

    @Column(nullable = false)
    private String authenticationMethod;

    @Column(nullable = false)
    private Double conversionRate;

    @Column(nullable = false)
    private boolean isEnabled = true;

    @Column(nullable = false)
    private String status = "ACTIVE";
} 