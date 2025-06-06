package com.loyalbridge.adminpanel.service;

import com.loyalbridge.adminpanel.dto.ConversionLogResponse;
import com.loyalbridge.adminpanel.model.Transaction;
import com.loyalbridge.adminpanel.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConversionLogService {
    private final TransactionRepository transactionRepository;

    @Transactional(readOnly = true)
    public Page<ConversionLogResponse> getConversionLogs(
            Long userId,
            Long partnerId,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable) {
        log.info("Fetching conversion logs with filters: userId={}, partnerId={}, startDate={}, endDate={}",
                userId, partnerId, startDate, endDate);

        Page<Transaction> transactions = transactionRepository.searchTransactions(
                partnerId, userId, null, "CONVERSION", startDate, endDate, pageable);

        return transactions.map(this::mapToConversionLogResponse);
    }

    @Transactional(readOnly = true)
    public List<ConversionLogResponse> exportConversionLogs(
            Long userId,
            Long partnerId,
            LocalDateTime startDate,
            LocalDateTime endDate) {
        log.info("Exporting conversion logs with filters: userId={}, partnerId={}, startDate={}, endDate={}",
                userId, partnerId, startDate, endDate);

        List<Transaction> transactions = transactionRepository.findByPartnerIdAndDateRange(
                partnerId, startDate, endDate);

        return transactions.stream()
                .filter(t -> userId == null || t.getUser().getId().equals(userId))
                .filter(t -> "CONVERSION".equals(t.getType()))
                .map(this::mapToConversionLogResponse)
                .collect(Collectors.toList());
    }

    private ConversionLogResponse mapToConversionLogResponse(Transaction transaction) {
        ConversionLogResponse response = new ConversionLogResponse();
        response.setId(transaction.getId());
        response.setPartnerName(transaction.getPartner().getName());
        response.setUserName(transaction.getUser().getName());
        response.setAmount(transaction.getAmount());
        response.setPoints(transaction.getPoints());
        response.setStatus(transaction.getStatus());
        response.setType(transaction.getType());
        response.setTimestamp(transaction.getTimestamp());
        response.setDescription(transaction.getDescription());
        response.setReferenceId(transaction.getReferenceId());
        response.setErrorMessage(transaction.getErrorMessage());
        return response;
    }
} 