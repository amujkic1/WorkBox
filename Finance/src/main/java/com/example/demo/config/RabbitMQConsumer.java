package com.example.demo.config;

import com.example.demo.dto.UserDTO;
import com.example.demo.models.User;
import com.example.demo.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {

    private final UserService userService;

    public RabbitMQConsumer(UserService userService) {
        this.userService = userService;
    }


    @RabbitListener(queues = "user-registration-finance")
    public void consumeJSON(UserDTO userDTO){
        System.out.println("\n----------------");
        System.out.println("Primljena poruka je: "+userDTO);

        User user = new User(userDTO.getFirstName(),
                userDTO.getLastName(),
                0.0);
        user.setUserUUID(userDTO.getUuid());

        userService.insert(user);

    }

}
