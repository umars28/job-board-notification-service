package com.job.board.notification.service.model;

import lombok.Data;

@Data
public class NotificationResponse {
    private Long id;
    private String message;
    private String link;
    private Boolean isRead;
    private String createdAt;
}
