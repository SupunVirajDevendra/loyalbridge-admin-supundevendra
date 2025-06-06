package com.loyalbridge.adminpanel.repository;

import com.loyalbridge.adminpanel.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    UserProfile findByUserId(Long userId);
} 