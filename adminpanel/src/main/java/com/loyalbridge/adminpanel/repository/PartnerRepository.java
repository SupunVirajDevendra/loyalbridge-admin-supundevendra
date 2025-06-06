package com.loyalbridge.adminpanel.repository;

import com.loyalbridge.adminpanel.model.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {
    List<Partner> findByStatus(String status);
    List<Partner> findByIsEnabled(boolean isEnabled);
} 