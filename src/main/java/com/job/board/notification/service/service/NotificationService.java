package com.job.board.notification.service.service;

import com.job.board.notification.service.entity.Notification;
import com.job.board.notification.service.model.NotificationResponse;
import com.job.board.notification.service.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<NotificationResponse> get5LatestNotification(String username) {
        return notificationRepository.findTop5ByReceiverUsernameOrderByCreatedAtDesc(username)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public Long countUnreadNotificationByUsername(String username) {
        return notificationRepository.countByReceiverUsernameAndIsReadFalse(username);
    }

    public void markAllAsRead(String username) {
        List<Notification> unread = notificationRepository.findByReceiverUsernameAndIsReadFalse(username);
        unread.forEach(n -> n.setIsRead(true));
        notificationRepository.saveAll(unread);
    }

    public Notification markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        if (!notification.getIsRead()) {
            notification.setIsRead(true);
            notificationRepository.save(notification);
        }

        return notification;
    }

    private NotificationResponse toResponse(Notification entity) {
        NotificationResponse res = new NotificationResponse();
        res.setId(entity.getId());
        res.setMessage(entity.getMessage());
        res.setLink(entity.getLink());
        res.setIsRead(entity.getIsRead());
        res.setCreatedAt(entity.getCreatedAt().toString());
        return res;
    }
}
