package com.example.business.messaging;

import com.example.business.events.UserCreatedEvent;
import com.example.business.model.User;
import com.example.business.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class UserEventConsumer {

    private final UserService userService;

    public UserEventConsumer(UserService userService) {
        this.userService = userService;
    }

    @RabbitListener(queues = "user.created.queue")
    public void handleUserCreatedEvent(UserCreatedEvent event) {
        System.out.println("Received UserCreatedEvent: " + event.getUuid());

        // Kreirajte User entitet iz UserCreatedEvent
        User user = new User();
        user.setUuid(event.getUuid());
        user.setFirstName(event.getFirstName());
        user.setLastName(event.getLastName());

        // Spremite korisnika u bazu
        userService.saveUser(user);
    }
}
