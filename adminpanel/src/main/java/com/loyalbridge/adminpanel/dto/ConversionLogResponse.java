package com.loyalbridge.adminpanel.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ConversionLogResponse {
    private Long id;
    private String partnerName;
    private String userName;
    private Double amount;
    private Integer points;
    private String status;
    private String type;
    private LocalDateTime timestamp;
    private String description;
    private String referenceId;
    private String errorMessage;
} 