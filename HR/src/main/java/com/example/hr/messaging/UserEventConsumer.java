package com.example.hr.messaging;

import com.example.hr.events.UserCreatedEvent;
import com.example.hr.model.User;
import com.example.hr.service.UserService;
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
        user.setEmail(event.getEmail());
        user.setUsername(event.getUsername());
        user.setPassword(event.getPassword());

        System.out.println("userrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
        System.out.println(user);

        // Spremite korisnika u bazu
        userService.saveUser(user);
    }
}
