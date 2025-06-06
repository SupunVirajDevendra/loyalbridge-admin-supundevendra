package com.loyalbridge.adminpanel.controller;

import com.loyalbridge.adminpanel.dto.DashboardResponse;
import com.loyalbridge.adminpanel.service.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('PARTNER_ADMIN')")
    public ResponseEntity<DashboardResponse> getDashboardData() {
        log.info("Getting dashboard data");
        return ResponseEntity.ok(dashboardService.getDashboardData());
    }
} 