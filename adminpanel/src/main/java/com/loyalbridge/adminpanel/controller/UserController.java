package com.loyalbridge.adminpanel.controller;

import com.loyalbridge.adminpanel.model.User;
import com.loyalbridge.adminpanel.model.UserProfile;
import com.loyalbridge.adminpanel.model.ActivityLog;
import com.loyalbridge.adminpanel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') or @userService.getUserById(#userId).get().email == authentication.principal")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') or @userService.getUserById(#userId).get().email == authentication.principal")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User userDetails) {
        return ResponseEntity.ok(userService.updateUser(userId, userDetails));
    }

    @PutMapping("/{userId}/profile")
    @PreAuthorize("hasRole('ADMIN') or @userService.getUserById(#userId).get().email == authentication.principal")
    public ResponseEntity<UserProfile> updateUserProfile(@PathVariable Long userId, @RequestBody UserProfile profileDetails) {
        return ResponseEntity.ok(userService.updateUserProfile(userId, profileDetails));
    }

    @GetMapping("/{userId}/activity")
    @PreAuthorize("hasRole('ADMIN') or @userService.getUserById(#userId).get().email == authentication.principal")
    public ResponseEntity<List<ActivityLog>> getUserActivityLogs(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserActivityLogs(userId));
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
} 