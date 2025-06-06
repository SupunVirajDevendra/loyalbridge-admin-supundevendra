package com.loyalbridge.adminpanel.repository;

import com.loyalbridge.adminpanel.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE " +
           "(:query IS NULL OR LOWER(u.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(u.phone) LIKE LOWER(CONCAT('%', :query, '%'))) AND " +
           "(:status IS NULL OR u.status = :status) AND " +
           "(:riskLevel IS NULL OR u.riskLevel = :riskLevel)")
    Page<User> searchUsers(
            @Param("query") String query,
            @Param("status") User.Status status,
            @Param("riskLevel") User.RiskLevel riskLevel,
            Pageable pageable
    );
}