package com.job.board.notification.service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private final RabbitMQProperties properties;

    public RabbitMQConfig(RabbitMQProperties properties) {
        this.properties = properties;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter messageConverter) {

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return factory;
    }

    @Bean
    public DirectExchange notificationExchange() {
        return new DirectExchange(properties.getNotificationExchange(), true, false);
    }

    @Bean
    public Queue companyQueue() {
        return QueueBuilder.durable(properties.getCompanyQueue())
                .withArgument("x-dead-letter-exchange", "dlx-exchange")
                .build();
    }

    @Bean
    public Queue jobSeekerQueue() {
        return QueueBuilder.durable(properties.getJobSeekerQueue())
                .withArgument("x-dead-letter-exchange", "dlx-exchange")
                .build();
    }

    @Bean
    public Binding companyBinding(Queue companyQueue, DirectExchange notificationExchange) {
        return BindingBuilder.bind(companyQueue)
                .to(notificationExchange)
                .with(properties.getNotifyCompanyRoutingKey());
    }

    @Bean
    public Binding jobSeekerBinding(Queue jobSeekerQueue, DirectExchange notificationExchange) {
        return BindingBuilder.bind(jobSeekerQueue)
                .to(notificationExchange)
                .with(properties.getNotifyJobSeekerRoutingKey());
    }

    @Bean
    public TopicExchange deadLetterExchange() {
        return new TopicExchange("dlx-exchange");
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable("dead-letter-queue").build();
    }

    @Bean
    public Binding dlqBinding() {
        return BindingBuilder.bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with("#");
    }

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }
}
