package com.loyalbridge.adminpanel.service;

import com.loyalbridge.adminpanel.dto.PartnerRequest;
import com.loyalbridge.adminpanel.model.Partner;
import com.loyalbridge.adminpanel.repository.PartnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartnerService {
    private final PartnerRepository partnerRepository;

    public List<Partner> getAllPartners() {
        return partnerRepository.findAll();
    }

    public Optional<Partner> getPartnerById(Long id) {
        return partnerRepository.findById(id);
    }

    public Partner createPartner(PartnerRequest request) {
        Partner partner = new Partner();
        partner.setName(request.getName());
        partner.setApiUrl(request.getApiUrl());
        partner.setAuthenticationMethod(request.getAuthenticationMethod());
        partner.setConversionRate(request.getConversionRate());
        partner.setEnabled(request.isEnabled());
        partner.setStatus(request.getStatus());
        return partnerRepository.save(partner);
    }

    public Partner updatePartner(Long id, PartnerRequest request) {
        Partner partner = partnerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partner not found"));
        
        partner.setName(request.getName());
        partner.setApiUrl(request.getApiUrl());
        partner.setAuthenticationMethod(request.getAuthenticationMethod());
        partner.setConversionRate(request.getConversionRate());
        partner.setEnabled(request.isEnabled());
        partner.setStatus(request.getStatus());
        
        return partnerRepository.save(partner);
    }

    public void deletePartner(Long id) {
        partnerRepository.deleteById(id);
    }

    public List<Partner> getPartnersByStatus(String status) {
        return partnerRepository.findByStatus(status);
    }

    public List<Partner> getEnabledPartners() {
        return partnerRepository.findByIsEnabled(true);
    }
} 