package com.example.hr.messaging;

import com.example.hr.events.UserCreatedEvent;
import com.example.hr.events.UserCreationFailedEvent;
import com.example.hr.model.User;
import com.example.hr.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserEventConsumer {

    private final UserService userService;
    private final RabbitTemplate rabbitTemplate;

    public UserEventConsumer(UserService userService, RabbitTemplate rabbitTemplate) {
        this.userService = userService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "hr.user.created.queue")
    public void handleUserCreatedEvent(UserCreatedEvent event) {
        try {
            System.out.println("Received UserCreatedEvent: " + event.getUuid());

            // Simulacija greške:
            //throw new RuntimeException("Simulirana greška u HR servisu");

            User user = new User();
            user.setUuid(event.getUuid());
            user.setFirstName(event.getFirstName());
            user.setLastName(event.getLastName());
            user.setEmail(event.getEmail());
            user.setUsername(event.getUsername());
            user.setPassword(event.getPassword());

            userService.saveUser(user);
        } catch (Exception e) {
            System.out.println("Greška u HR servisu: " + e.getMessage());
            UserCreationFailedEvent rollbackEvent = new UserCreationFailedEvent(event.getUuid(), e.getMessage());
            rabbitTemplate.convertAndSend("user.event.exchange", "auth.user.creation.failed", rollbackEvent);
            System.out.println("Rollback event poslan!");
        }
    }
}