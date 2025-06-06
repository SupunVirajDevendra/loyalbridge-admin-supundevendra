package com.loyalbridge.adminpanel.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartnerRequest {
    private String name;
    private String apiUrl;
    private String authenticationMethod;
    private Double conversionRate;
    private boolean isEnabled = true;
    private String status = "ACTIVE";
} 