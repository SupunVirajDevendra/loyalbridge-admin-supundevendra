package com.loyalbridge.adminpanel.service;

import com.loyalbridge.adminpanel.dto.DashboardResponse;
import com.loyalbridge.adminpanel.model.Transaction;
import com.loyalbridge.adminpanel.model.User;
import com.loyalbridge.adminpanel.repository.TransactionRepository;
import com.loyalbridge.adminpanel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardService {
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private static final DateTimeFormatter WEEK_FORMATTER = DateTimeFormatter.ofPattern("'Week' w, yyyy");

    @Transactional(readOnly = true)
    public DashboardResponse getDashboardData() {
        log.info("Fetching dashboard data");
        
        DashboardResponse response = new DashboardResponse();
        response.setStats(calculateStats());
        response.setRecentTransactions(getRecentTransactions());
        response.setWeeklyConversions(getWeeklyConversions());
        
        return response;
    }

    private DashboardResponse.DashboardStats calculateStats() {
        DashboardResponse.DashboardStats stats = new DashboardResponse.DashboardStats();
        
        // Calculate total users
        stats.setTotalUsers(userRepository.count());
        
        // Calculate total points and converted amount from transactions
        List<Transaction> allTransactions = transactionRepository.findAll();
        long totalPoints = 0;
        double totalConvertedAmount = 0;
        
        for (Transaction transaction : allTransactions) {
            if ("CONVERSION".equals(transaction.getType()) && "COMPLETED".equals(transaction.getStatus())) {
                totalPoints += transaction.getPoints();
                totalConvertedAmount += transaction.getAmount();
            }
        }
        
        stats.setTotalPoints(totalPoints);
        stats.setTotalConvertedAmount(totalConvertedAmount);
        
        return stats;
    }

    private List<DashboardResponse.RecentTransaction> getRecentTransactions() {
        return transactionRepository.findTop10ByOrderByTimestampDesc().stream()
                .map(this::mapToRecentTransaction)
                .collect(Collectors.toList());
    }

    private DashboardResponse.RecentTransaction mapToRecentTransaction(Transaction transaction) {
        DashboardResponse.RecentTransaction recentTransaction = new DashboardResponse.RecentTransaction();
        recentTransaction.setId(transaction.getId());
        recentTransaction.setPartnerName(transaction.getPartner().getName());
        recentTransaction.setUserName(transaction.getUser().getName());
        recentTransaction.setAmount(transaction.getAmount());
        recentTransaction.setPoints(transaction.getPoints());
        recentTransaction.setStatus(transaction.getStatus());
        recentTransaction.setTimestamp(transaction.getTimestamp());
        return recentTransaction;
    }

    private List<DashboardResponse.WeeklyConversion> getWeeklyConversions() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sixWeeksAgo = now.minusWeeks(6);
        
        List<Transaction> recentTransactions = transactionRepository.findByTimestampBetween(sixWeeksAgo, now);
        
        Map<String, List<Transaction>> transactionsByWeek = recentTransactions.stream()
                .filter(t -> "CONVERSION".equals(t.getType()))
                .collect(Collectors.groupingBy(t -> t.getTimestamp().format(WEEK_FORMATTER)));
        
        return transactionsByWeek.entrySet().stream()
                .map(entry -> {
                    DashboardResponse.WeeklyConversion weeklyConversion = new DashboardResponse.WeeklyConversion();
                    weeklyConversion.setWeek(entry.getKey());
                    
                    List<Transaction> weekTransactions = entry.getValue();
                    weeklyConversion.setTransactionCount(weekTransactions.size());
                    
                    double totalAmount = weekTransactions.stream()
                            .mapToDouble(Transaction::getAmount)
                            .sum();
                    weeklyConversion.setTotalAmount(totalAmount);
                    
                    int totalPoints = weekTransactions.stream()
                            .mapToInt(Transaction::getPoints)
                            .sum();
                    weeklyConversion.setTotalPoints(totalPoints);
                    
                    return weeklyConversion;
                })
                .sorted((w1, w2) -> {
                    LocalDateTime date1 = LocalDateTime.parse(w1.getWeek().replace("Week ", ""), 
                            DateTimeFormatter.ofPattern("w, yyyy"));
                    LocalDateTime date2 = LocalDateTime.parse(w2.getWeek().replace("Week ", ""), 
                            DateTimeFormatter.ofPattern("w, yyyy"));
                    return date1.compareTo(date2);
                })
                .collect(Collectors.toList());
    }
} 