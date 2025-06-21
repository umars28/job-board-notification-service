package com.job.board.notification.service.repository;

import com.job.board.notification.service.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findTop5ByReceiverUsernameOrderByCreatedAtDesc(String username);
    List<Notification> findByReceiverUsernameAndIsReadFalse(String username);
    long countByReceiverUsernameAndIsReadFalse(String username);
}
