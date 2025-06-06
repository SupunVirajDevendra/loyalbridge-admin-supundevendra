package com.loyalbridge.adminpanel.controller;

import com.loyalbridge.adminpanel.model.Transaction;
import com.loyalbridge.adminpanel.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("/partner/{partnerId}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('PARTNER_ADMIN')")
    public ResponseEntity<List<Transaction>> getPartnerTransactions(@PathVariable Long partnerId) {
        log.info("Getting transactions for partner: {}", partnerId);
        return ResponseEntity.ok(transactionService.getPartnerTransactions(partnerId));
    }

    @GetMapping("/partner/{partnerId}/date-range")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('PARTNER_ADMIN')")
    public ResponseEntity<List<Transaction>> getPartnerTransactionsByDateRange(
            @PathVariable Long partnerId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        log.info("Getting transactions for partner: {} between {} and {}", partnerId, startDate, endDate);
        return ResponseEntity.ok(transactionService.getPartnerTransactionsByDateRange(partnerId, startDate, endDate));
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('PARTNER_ADMIN')")
    public ResponseEntity<Page<Transaction>> searchTransactions(
            @RequestParam(required = false) Long partnerId,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            Pageable pageable) {
        log.info("Searching transactions with filters: partnerId={}, userId={}, status={}, type={}, startDate={}, endDate={}",
                partnerId, userId, status, type, startDate, endDate);
        return ResponseEntity.ok(transactionService.searchTransactions(
                partnerId, userId, status, type, startDate, endDate, pageable));
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('PARTNER_ADMIN')")
    public ResponseEntity<Transaction> createTransaction(
            @RequestParam Long partnerId,
            @RequestParam Long userId,
            @RequestParam Double amount,
            @RequestParam Integer points,
            @RequestParam String type,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String referenceId) {
        log.info("Creating transaction for partner: {} and user: {}", partnerId, userId);
        return ResponseEntity.ok(transactionService.createTransaction(
                partnerId, userId, amount, points, type, description, referenceId));
    }

    @PutMapping("/{transactionId}/status")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Transaction> updateTransactionStatus(
            @PathVariable Long transactionId,
            @RequestParam String status,
            @RequestParam(required = false) String errorMessage) {
        log.info("Updating transaction status: {} to {}", transactionId, status);
        return ResponseEntity.ok(transactionService.updateTransactionStatus(transactionId, status, errorMessage));
    }
} 