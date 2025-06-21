package com.job.board.notification.service.service;

import com.job.board.notification.service.entity.Notification;
import com.job.board.notification.service.model.NotificationPayload;
import com.job.board.notification.service.repository.NotificationRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationListener {

    private final NotificationRepository notificationRepository;

    public NotificationListener(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @RabbitListener(queues = "#{rabbitMQProperties.companyQueue}")
    public void handleCompanyNotification(NotificationPayload payload) {
        Notification notification = new Notification();
        notification.setMessage(payload.getMessage());
        notification.setLink(payload.getLink());
        notification.setCreatedAt(LocalDateTime.now());
        notification.setReceiverUsername(payload.getReceiverUsername());
        notification.setIsRead(false);

        notificationRepository.save(notification);
        System.out.println("📥 Received Company Notification: " + payload);
    }

    @RabbitListener(queues = "#{rabbitMQProperties.jobSeekerQueue}")
    public void handleJobSeekerNotification(NotificationPayload payload) {
        Notification notification = new Notification();
        notification.setMessage(payload.getMessage());
        notification.setLink(payload.getLink());
        notification.setCreatedAt(LocalDateTime.now());
        notification.setReceiverUsername(payload.getReceiverUsername());
        notification.setIsRead(false);

        notificationRepository.save(notification);
        System.out.println("📥 Received Job Seeker Notification: " + payload);
    }
}

