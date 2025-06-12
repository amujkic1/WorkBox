package com.example.demo.messaging;

import com.example.demo.events.UserCreatedEvent;
import com.example.demo.models.User;
import com.example.demo.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class UserEventConsumer {

    private final UserService userService;

    public UserEventConsumer(UserService userService) {
        this.userService = userService;
    }

    @RabbitListener(queues = "finance.manager.user.created.queue")
    public void handleUserCreatedEvent(UserCreatedEvent event) {
        System.out.println("Received UserCreatedEvent: " + event.getUuid());

        User user = new User();
        user.setUserUUID(event.getUuid());
        user.setFirstName(event.getFirstName());
        user.setLastName(event.getLastName());

        userService.saveUser(user);
    }
}

