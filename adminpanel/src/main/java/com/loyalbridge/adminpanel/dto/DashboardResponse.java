package com.loyalbridge.adminpanel.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class DashboardResponse {
    private DashboardStats stats;
    private List<RecentTransaction> recentTransactions;
    private List<WeeklyConversion> weeklyConversions;

    @Data
    public static class DashboardStats {
        private long totalUsers;
        private long totalPoints;
        private double totalConvertedAmount;
    }

    @Data
    public static class RecentTransaction {
        private Long id;
        private String partnerName;
        private String userName;
        private Double amount;
        private Integer points;
        private String status;
        private LocalDateTime timestamp;
    }

    @Data
    public static class WeeklyConversion {
        private String week;
        private double totalAmount;
        private int totalPoints;
        private int transactionCount;
    }
} 