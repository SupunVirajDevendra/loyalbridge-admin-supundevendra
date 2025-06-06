package com.loyalbridge.adminpanel.repository;

import com.loyalbridge.adminpanel.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByPartnerId(Long partnerId);
    
    List<Transaction> findByUserId(Long userId);
    
    @Query("SELECT t FROM Transaction t WHERE t.partner.id = :partnerId AND t.timestamp BETWEEN :startDate AND :endDate")
    List<Transaction> findByPartnerIdAndDateRange(
            @Param("partnerId") Long partnerId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
    
    @Query("SELECT t FROM Transaction t WHERE " +
           "(:partnerId IS NULL OR t.partner.id = :partnerId) AND " +
           "(:userId IS NULL OR t.user.id = :userId) AND " +
           "(:status IS NULL OR t.status = :status) AND " +
           "(:type IS NULL OR t.type = :type) AND " +
           "(:startDate IS NULL OR t.timestamp >= :startDate) AND " +
           "(:endDate IS NULL OR t.timestamp <= :endDate)")
    Page<Transaction> searchTransactions(
            @Param("partnerId") Long partnerId,
            @Param("userId") Long userId,
            @Param("status") String status,
            @Param("type") String type,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );

    List<Transaction> findTop10ByOrderByTimestampDesc();

    List<Transaction> findByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate);
} 