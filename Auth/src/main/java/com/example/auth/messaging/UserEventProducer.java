package com.example.auth.messaging;

import com.example.auth.config.RabbitMQConfig;
import com.example.auth.events.UserCreateConfirmedEvent;
import com.example.auth.events.UserCreateRollbackEvent;
import com.example.auth.events.UserCreatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserEventProducer {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public UserEventProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    // Metoda za slanje UserCreatedEvent sa JSON serijalizacijom
    public void sendUserCreatedEvent(UserCreatedEvent event) {
        try {
            // Koristimo Jackson2JsonMessageConverter za konvertovanje objekta u JSON
            rabbitTemplate.convertAndSend(RabbitMQConfig.USER_EVENT_EXCHANGE, "user.created", event);
        } catch (Exception e) {
            e.printStackTrace();  // Obrada greške (logovanje, ponovni pokušaj itd.)
        }
    }

    // Metoda za slanje UserCreateRollbackEvent sa JSON serijalizacijom
    public void sendUserCreateRollbackEvent(UserCreateRollbackEvent event) {
        try {
            // Koristimo Jackson2JsonMessageConverter za konvertovanje objekta u JSON
            rabbitTemplate.convertAndSend(RabbitMQConfig.USER_EVENT_EXCHANGE, "user.create.rollback", event);
        } catch (Exception e) {
            e.printStackTrace();  // Obrada greške (logovanje, ponovni pokušaj itd.)
        }
    }

    // Metoda za slanje UserCreateConfirmedEvent sa JSON serijalizacijom
    public void sendUserCreateConfirmedEvent(UserCreateConfirmedEvent event) {
        try {
            // Koristimo Jackson2JsonMessageConverter za konvertovanje objekta u JSON
            rabbitTemplate.convertAndSend(RabbitMQConfig.USER_EVENT_EXCHANGE, "user.create.confirmed", event);
        } catch (Exception e) {
            e.printStackTrace();  // Obrada greške (logovanje, ponovni pokušaj itd.)
        }
    }
}
