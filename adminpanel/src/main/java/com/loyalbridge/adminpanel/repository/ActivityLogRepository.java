package com.loyalbridge.adminpanel.repository;

import com.loyalbridge.adminpanel.model.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
    List<ActivityLog> findByUserIdOrderByTimestampDesc(Long userId);
    List<ActivityLog> findByActionOrderByTimestampDesc(String action);
} 