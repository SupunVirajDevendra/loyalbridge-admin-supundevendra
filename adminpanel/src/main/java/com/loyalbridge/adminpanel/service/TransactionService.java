package com.loyalbridge.adminpanel.service;

import com.loyalbridge.adminpanel.model.Transaction;
import com.loyalbridge.adminpanel.model.Partner;
import com.loyalbridge.adminpanel.model.User;
import com.loyalbridge.adminpanel.repository.TransactionRepository;
import com.loyalbridge.adminpanel.repository.PartnerRepository;
import com.loyalbridge.adminpanel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final PartnerRepository partnerRepository;
    private final UserRepository userRepository;

    public List<Transaction> getPartnerTransactions(Long partnerId) {
        log.info("Getting transactions for partner: {}", partnerId);
        return transactionRepository.findByPartnerId(partnerId);
    }

    public List<Transaction> getPartnerTransactionsByDateRange(
            Long partnerId,
            LocalDateTime startDate,
            LocalDateTime endDate) {
        log.info("Getting transactions for partner: {} between {} and {}", partnerId, startDate, endDate);
        return transactionRepository.findByPartnerIdAndDateRange(partnerId, startDate, endDate);
    }

    public Page<Transaction> searchTransactions(
            Long partnerId,
            Long userId,
            String status,
            String type,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable) {
        log.info("Searching transactions with filters: partnerId={}, userId={}, status={}, type={}, startDate={}, endDate={}",
                partnerId, userId, status, type, startDate, endDate);
        return transactionRepository.searchTransactions(
                partnerId, userId, status, type, startDate, endDate, pageable);
    }

    @Transactional
    public Transaction createTransaction(
            Long partnerId,
            Long userId,
            Double amount,
            Integer points,
            String type,
            String description,
            String referenceId) {
        log.info("Creating transaction for partner: {} and user: {}", partnerId, userId);
        
        Partner partner = partnerRepository.findById(partnerId)
                .orElseThrow(() -> new RuntimeException("Partner not found"));
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Transaction transaction = new Transaction();
        transaction.setPartner(partner);
        transaction.setUser(user);
        transaction.setAmount(amount);
        transaction.setPoints(points);
        transaction.setType(type);
        transaction.setStatus("PENDING");
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setDescription(description);
        transaction.setReferenceId(referenceId);

        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction updateTransactionStatus(Long transactionId, String status, String errorMessage) {
        log.info("Updating transaction status: {} to {}", transactionId, status);
        
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        
        transaction.setStatus(status);
        if (errorMessage != null) {
            transaction.setErrorMessage(errorMessage);
        }
        
        return transactionRepository.save(transaction);
    }
} 