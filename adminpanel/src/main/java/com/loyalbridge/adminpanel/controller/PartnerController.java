package com.loyalbridge.adminpanel.controller;

import com.loyalbridge.adminpanel.dto.PartnerRequest;
import com.loyalbridge.adminpanel.model.Partner;
import com.loyalbridge.adminpanel.service.PartnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/partners")
@RequiredArgsConstructor
public class PartnerController {
    private final PartnerService partnerService;

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('PARTNER_ADMIN')")
    public ResponseEntity<List<Partner>> getAllPartners() {
        return ResponseEntity.ok(partnerService.getAllPartners());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('PARTNER_ADMIN')")
    public ResponseEntity<Partner> getPartnerById(@PathVariable Long id) {
        return partnerService.getPartnerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Partner> createPartner(@RequestBody PartnerRequest request) {
        return ResponseEntity.ok(partnerService.createPartner(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Partner> updatePartner(@PathVariable Long id, @RequestBody PartnerRequest request) {
        return ResponseEntity.ok(partnerService.updatePartner(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deletePartner(@PathVariable Long id) {
        partnerService.deletePartner(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('PARTNER_ADMIN')")
    public ResponseEntity<List<Partner>> getPartnersByStatus(@PathVariable String status) {
        return ResponseEntity.ok(partnerService.getPartnersByStatus(status));
    }

    @GetMapping("/enabled")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('PARTNER_ADMIN')")
    public ResponseEntity<List<Partner>> getEnabledPartners() {
        return ResponseEntity.ok(partnerService.getEnabledPartners());
    }
} 