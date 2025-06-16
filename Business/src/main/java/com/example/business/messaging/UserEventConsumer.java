package com.example.business.messaging;

import com.example.business.events.UserCreatedEvent;
import com.example.business.events.UserCreationFailedEvent;
import com.example.business.model.User;
import com.example.business.service.UserService;
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

    @RabbitListener(queues = "business.manager.user.created.queue")
    public void handleUserCreatedEvent(UserCreatedEvent event) {
        try {
            System.out.println("[Business Service] Primljen event: " + event.getUuid());

            //throw new RuntimeException("Simulirana greška u Business servisu");

            User user = new User();
            user.setUuid(event.getUuid());
            user.setFirstName(event.getFirstName());
            user.setLastName(event.getLastName());

            userService.saveUser(user);

            System.out.println("[Business Service] Korisnik uspješno sačuvan: " + user.getFirstName());

        } catch (Exception e) {
            System.out.println("[Business Service] Greška: " + e.getMessage());

            UserCreationFailedEvent rollbackEvent = new UserCreationFailedEvent(event.getUuid(), e.getMessage());
            rabbitTemplate.convertAndSend("user.event.exchange", "auth.user.creation.failed", rollbackEvent);

            System.out.println("[Business Service] Poslan rollback za UUID: " + event.getUuid());
        }
    }
}