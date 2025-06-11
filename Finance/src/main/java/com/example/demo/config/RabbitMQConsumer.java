package com.example.demo.config;

import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserRegistrationStatusDTO;
import com.example.demo.models.User;
import com.example.demo.service.UserService;
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

    private void sendStatus(UUID uuid, String service, boolean status) {
        UserRegistrationStatusDTO statusEvent = new UserRegistrationStatusDTO(uuid, service, status);
        rabbitTemplate.convertAndSend(statusExchange, statusRoutingKey, statusEvent);
    }


    @RabbitListener(queues = "user-registration-finance", containerFactory = "rabbitListenerContainerFactory")
    public void consumeJSON(UserDTO userDTO){
        System.out.println("\n------------------ PRIMLJEN USER ------------------\n" + userDTO);

        try {
            User user = new User(userDTO.getFirstName(),
                    userDTO.getLastName(),
                    0.0);
            user.setUserUUID(userDTO.getUuid());
            userService.insert(user);

            // Slanje SUCCESS status događaja
            sendStatus(userDTO.getUuid(), "finance", true);
        }
        catch (Exception e) {
            System.err.println("Greška pri upisu u bazu! Slanje FAILURE događaja.");
            sendStatus(userDTO.getUuid(), "finance", false);
        }

    }


    // Listener koji reaguje na rollback (FAILURE) događaje
    @RabbitListener(queues = "user-registration-status.finance", containerFactory = "rabbitListenerContainerFactory")
    public void handleStatus(UserRegistrationStatusDTO event) {
        System.out.println("\n--------------STATUS--------------");
        System.out.println("Primljen status događaj: " + event);
        System.out.println("\n");

        // Samo ako je neko drugi poslao FAILURE, brišemo korisnika
        if (!event.isSuccessStatus() && !"finance".equals(event.getService())) {
            try {
                userService.deleteUserByUUID(event.getUserUUID());
                System.out.println("Rollback uspešno izvršen za UUID: " + event.getUserUUID());
            }
            catch (Exception e) {
                System.err.println("Greška pri rollback-u za UUID: " + event.getUserUUID());
            }
        }
    }

}
