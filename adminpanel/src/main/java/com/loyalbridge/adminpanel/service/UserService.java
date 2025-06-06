package com.loyalbridge.adminpanel.service;

import com.loyalbridge.adminpanel.model.User;
import com.loyalbridge.adminpanel.model.UserProfile;
import com.loyalbridge.adminpanel.model.ActivityLog;
import com.loyalbridge.adminpanel.repository.UserRepository;
import com.loyalbridge.adminpanel.repository.UserProfileRepository;
import com.loyalbridge.adminpanel.repository.ActivityLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
} 