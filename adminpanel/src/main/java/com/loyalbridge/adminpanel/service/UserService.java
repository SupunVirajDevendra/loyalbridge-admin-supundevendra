package com.loyalbridge.adminpanel.service;

import com.loyalbridge.adminpanel.dto.UserProfileDTO;
import com.loyalbridge.adminpanel.model.User;
import com.loyalbridge.adminpanel.model.UserProfile;
import com.loyalbridge.adminpanel.model.ActivityLog;
import com.loyalbridge.adminpanel.repository.UserRepository;
import com.loyalbridge.adminpanel.repository.UserProfileRepository;
import com.loyalbridge.adminpanel.repository.ActivityLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final ActivityLogRepository activityLogRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User updateUser(Long userId, User userDetails) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        user.setEmail(userDetails.getEmail());
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }
        user.setRole(userDetails.getRole());
        user.setActive(userDetails.isActive());
        user.setVerified(userDetails.isVerified());
        user.setHighRisk(userDetails.isHighRisk());
        
        logActivity(user, "UPDATE", "User profile updated");
        return userRepository.save(user);
    }

    @Transactional
    public UserProfile updateUserProfile(Long userId, UserProfile profileDetails) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        UserProfile profile = userProfileRepository.findByUserId(userId);
        if (profile == null) {
            profile = new UserProfile();
            profile.setUser(user);
        }
        
        profile.setFirstName(profileDetails.getFirstName());
        profile.setLastName(profileDetails.getLastName());
        profile.setPhoneNumber(profileDetails.getPhoneNumber());
        profile.setAddress(profileDetails.getAddress());
        profile.setCompany(profileDetails.getCompany());
        profile.setPosition(profileDetails.getPosition());
        profile.setNotes(profileDetails.getNotes());
        
        logActivity(user, "PROFILE_UPDATE", "User profile details updated");
        return userProfileRepository.save(profile);
    }

    public List<ActivityLog> getUserActivityLogs(Long userId) {
        return activityLogRepository.findByUserIdOrderByTimestampDesc(userId);
    }

    private void logActivity(User user, String action, String details) {
        ActivityLog log = new ActivityLog();
        log.setUser(user);
        log.setAction(action);
        log.setDetails(details);
        log.setTimestamp(LocalDateTime.now());
        log.setStatus("SUCCESS");
        
        // Get IP address from request
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String ipAddress = request.getHeader("X-Forwarded-For");
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
            }
            log.setIpAddress(ipAddress);
        }
        
        activityLogRepository.save(log);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        logActivity(user, "DELETE", "User account deleted");
        userRepository.delete(user);
    }

    public Page<UserProfileDTO> searchUsers(String query, User.Status status, User.RiskLevel riskLevel, Pageable pageable) {
        log.info("Searching users with query: {}, status: {}, riskLevel: {}", query, status, riskLevel);
        return userRepository.searchUsers(query, status, riskLevel, pageable)
                .map(UserProfileDTO::fromUser);
    }

    public Optional<UserProfileDTO> getUserProfile(Long userId) {
        log.info("Getting user profile for userId: {}", userId);
        return userRepository.findById(userId)
                .map(UserProfileDTO::fromUser);
    }

    @Transactional
    public UserProfileDTO updateUserStatus(Long userId, User.Status status) {
        log.info("Updating user status for userId: {} to {}", userId, status);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setStatus(status);
        return UserProfileDTO.fromUser(userRepository.save(user));
    }

    @Transactional
    public UserProfileDTO updateUserRiskLevel(Long userId, User.RiskLevel riskLevel) {
        log.info("Updating user risk level for userId: {} to {}", userId, riskLevel);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setRiskLevel(riskLevel);
        return UserProfileDTO.fromUser(userRepository.save(user));
    }

    @Transactional
    public UserProfileDTO toggleUserFreeze(Long userId) {
        log.info("Toggling user freeze status for userId: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setFrozen(!user.isFrozen());
        return UserProfileDTO.fromUser(userRepository.save(user));
    }

    @Transactional
    public UserProfileDTO updateUserVerification(Long userId, boolean verified) {
        log.info("Updating user verification status for userId: {} to {}", userId, verified);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setVerified(verified);
        return UserProfileDTO.fromUser(userRepository.save(user));
    }

    @Transactional
    public void updateLastLogin(Long userId) {
        log.info("Updating last login for userId: {}", userId);
        userRepository.findById(userId).ifPresent(user -> {
            user.setLastLoginAt(LocalDateTime.now());
            userRepository.save(user);
        });
    }
} 