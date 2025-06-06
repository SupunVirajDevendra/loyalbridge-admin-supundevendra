package com.loyalbridge.adminpanel.controller;

import com.loyalbridge.adminpanel.dto.UserProfileDTO;
import com.loyalbridge.adminpanel.model.User;
import com.loyalbridge.adminpanel.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/search")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('SUPPORT_STAFF')")
    public ResponseEntity<Page<UserProfileDTO>> searchUsers(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) User.Status status,
            @RequestParam(required = false) User.RiskLevel riskLevel,
            Pageable pageable) {
        log.info("Searching users with query: {}, status: {}, riskLevel: {}", query, status, riskLevel);
        return ResponseEntity.ok(userService.searchUsers(query, status, riskLevel, pageable));
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('SUPPORT_STAFF')")
    public ResponseEntity<UserProfileDTO> getUserProfile(@PathVariable Long userId) {
        log.info("Getting user profile for userId: {}", userId);
        return userService.getUserProfile(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{userId}/status")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<UserProfileDTO> updateUserStatus(
            @PathVariable Long userId,
            @RequestParam User.Status status) {
        log.info("Updating user status for userId: {} to {}", userId, status);
        return ResponseEntity.ok(userService.updateUserStatus(userId, status));
    }

    @PutMapping("/{userId}/risk-level")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<UserProfileDTO> updateUserRiskLevel(
            @PathVariable Long userId,
            @RequestParam User.RiskLevel riskLevel) {
        log.info("Updating user risk level for userId: {} to {}", userId, riskLevel);
        return ResponseEntity.ok(userService.updateUserRiskLevel(userId, riskLevel));
    }

    @PutMapping("/{userId}/freeze")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<UserProfileDTO> toggleUserFreeze(@PathVariable Long userId) {
        log.info("Toggling user freeze status for userId: {}", userId);
        return ResponseEntity.ok(userService.toggleUserFreeze(userId));
    }

    @PutMapping("/{userId}/verify")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<UserProfileDTO> updateUserVerification(
            @PathVariable Long userId,
            @RequestParam boolean verified) {
        log.info("Updating user verification status for userId: {} to {}", userId, verified);
        return ResponseEntity.ok(userService.updateUserVerification(userId, verified));
    }
} 