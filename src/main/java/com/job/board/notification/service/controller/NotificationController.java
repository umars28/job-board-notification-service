package com.job.board.notification.service.controller;

import com.job.board.notification.service.common.ApiResponse;
import com.job.board.notification.service.entity.Notification;
import com.job.board.notification.service.model.NotificationResponse;
import com.job.board.notification.service.repository.NotificationRepository;
import com.job.board.notification.service.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/notifications")
@RestController
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getAll(@RequestParam String username) {
        List<NotificationResponse> all = notificationService.getAllNotifications(username);
        ApiResponse<List<NotificationResponse>> response = new ApiResponse<>(200, "Success", all);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getLatest(@RequestParam String username) {
        List<NotificationResponse> list = notificationService.get5LatestNotification(username);
        ApiResponse<List<NotificationResponse>> response = new ApiResponse<>(200, "Success", list);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/unread-count")
    public ResponseEntity<ApiResponse<Long>> getUnreadCount(@RequestParam String username) {
        Long count = notificationService.countUnreadNotificationByUsername(username);
        ApiResponse<Long> response = new ApiResponse<>(200, "Success", count);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/mark-all-read")
    public ResponseEntity<ApiResponse<String>> markAllAsRead(@RequestParam String username) {
        notificationService.markAllAsRead(username);
        ApiResponse<String> response = new ApiResponse<>(200, "All notifications marked as read", null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/mark-read")
    public ResponseEntity<ApiResponse<Notification>> markAsRead(@PathVariable Long id) {
        Notification notification = notificationService.markAsRead(id);
        ApiResponse<Notification> response = new ApiResponse<>(200, "Notification marked as read", notification);
        return ResponseEntity.ok(response);
    }
}
