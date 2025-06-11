package com.example.business.config;

import com.example.business.dto.UserDTO;
import com.example.business.dto.UserRegistrationStatusDTO;
import com.example.business.model.User;
import com.example.business.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RabbitMQConsumer {

    private final UserService userService;
    private final RabbitTemplate rabbitTemplate;

    //private final String statusExchange = "registration-status";
    private final String statusRoutingKey = "status";

    private final String statusExchange = "registration.status.exchange"; // fanout exchange



    public RabbitMQConsumer(UserService userService, RabbitTemplate rabbitTemplate) {
        this.userService = userService;
        this.rabbitTemplate = rabbitTemplate;
    }


    private void sendStatus(UUID uuid, String service, boolean success) {
        UserRegistrationStatusDTO statusEvent = new UserRegistrationStatusDTO(uuid, service, success);
        rabbitTemplate.convertAndSend(statusExchange, statusRoutingKey, statusEvent);
    }



    @RabbitListener(queues = "user-registration-business")
    public void consumeJSON(UserDTO userDTO){
        System.out.println("\n--- BUSINESS MS PRIMIO USER ---\n" + userDTO);
        try {
            User user = new User(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getUuid());
            userService.saveUser(user);

            sendStatus(userDTO.getUuid(), "business", true);
        } catch (Exception e) {
            System.err.println("Greška u BUSINESS MS pri upisu! Slanje FAILURE događaja.");
            sendStatus(userDTO.getUuid(), "business", false);
        }
    }


    @RabbitListener(queues = "user-registration-status.business")
    public void handleStatus(UserRegistrationStatusDTO event) {
        System.out.println("\n--------------STATUS--------------");
        System.out.println("BUSINESS MS primio status događaj: " + event);
        System.out.println("\n");

        if (!event.isSuccessStatus() && !"business".equals(event.getService())) {
            try {
                userService.deleteUserByUUID(event.getUserUUID());
                System.out.println("Rollback u BUSINESS MS za UUID: " + event.getUserUUID());
            } catch (Exception e) {
                System.out.println("Izuzetak je");
                System.out.println(e);
                System.err.println("Greška u rollback-u BUSINESS MS: " + event.getUserUUID());

            }
        }
    }
}
