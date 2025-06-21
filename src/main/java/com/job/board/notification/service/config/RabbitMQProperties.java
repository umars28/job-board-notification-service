package com.job.board.notification.service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "app.rabbitmq")
public class RabbitMQProperties {

    private String notificationExchange;
    private String companyQueue;
    private String jobSeekerQueue;
    private String notifyCompanyRoutingKey;
    private String notifyJobSeekerRoutingKey;

}
