package com.example.business.config;

import com.example.business.dto.UserDTO;
import com.example.business.model.User;
import com.example.business.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {

    private final UserService userService;

    public RabbitMQConsumer(UserService userService) {
        this.userService = userService;
    }


    @RabbitListener(queues = "user-registration-business")
    public void consumeJSON(UserDTO userDTO){
        System.out.println("\n----------------");
        System.out.println("Primljena poruka je: "+userDTO.toString());

        User user = new User(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getUuid());
        userService.saveUser(user);

    }
}
