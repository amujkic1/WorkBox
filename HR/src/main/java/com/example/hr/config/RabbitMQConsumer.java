package com.example.hr.config;

import com.example.hr.dto.UserDTO2;
import com.example.hr.dto.UserRegistrationStatusDTO;
import com.example.hr.model.User;
import com.example.hr.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Service
public class RabbitMQConsumer {

    private final UserService userService;
    private final RabbitTemplate rabbitTemplate;

    //private final String statusExchange = "registration-status";
    private final String statusRoutingKey = "status";

    private final String statusExchange = "registration.status.exchange"; // novi fanout exchange


    public RabbitMQConsumer(UserService userService, RabbitTemplate rabbitTemplate) {
        this.userService = userService;
        this.rabbitTemplate = rabbitTemplate;
    }

    private void sendStatus(UUID uuid, String service, boolean success) {
        UserRegistrationStatusDTO statusEvent = new UserRegistrationStatusDTO(uuid, service, success);
        rabbitTemplate.convertAndSend(statusExchange, statusRoutingKey, statusEvent);
    }


    @RabbitListener(queues = "user-registration-hr")
    public void consumeJSON(UserDTO2 userDTO){
        System.out.println("\n--- HR MS PRIMIO USER ---\n" + userDTO);
        try {
            User user = User.builder()
                    .firstName(userDTO.getFirstName())  // Iz DTO
                    .lastName(userDTO.getLastName())    // Iz DTO
                    .username(userDTO.getUsername())    // Iz DTO
                    .email(userDTO.getEmail())          // Iz DTO
                    .password(userDTO.getPassword())    // Iz DTO
                    .role("USER")                       // Postaviti neku default vrednost (ako nije u DTO)
                    .jmbg(1000000000000L + new Random().nextLong(8999999999999L))                          // Postaviti default vrednost, možeš koristiti generisani JMBG
                    .birthDate(new Date())              // Postaviti default vrednost, npr. trenutni datum
                    .contactNumber("0000000000")        // Postaviti default vrednost
                    .address("Unknown")                 // Postaviti default vrednost
                    .employmentDate(new Date())         // Postaviti default vrednost, npr. trenutni datum
                    .status("ACTIVE")                   // Postaviti default vrednost
                    .workingHours(new Time(System.currentTimeMillis()))  // Postaviti default vrednost
                    .openings(new ArrayList<>())         // Postaviti praznu listu (ako ne dođe iz DTO)
                    .build();
            userService.insert(user);

            sendStatus(userDTO.getUuid(), "hr", true);
        } catch (Exception e) {
            System.err.println("Greška u HR MS pri upisu! Slanje FAILURE događaja.");
            sendStatus(userDTO.getUuid(), "hr", false);
        }
    }




    @RabbitListener(queues = "user-registration-status.hr")
    public void handleStatus(UserRegistrationStatusDTO event) {
        System.out.println("\n--------------STATUS--------------");
        System.out.println("HR MS primio status događaj: " + event);
        System.out.println("\n");

        if (!event.isSuccessStatus() && !"hr".equals(event.getService())) {
            try {
                //userService.deleteUserByUUID(event.getUserUUID());
                System.out.println("Rollback u HR MS za UUID: " + event.getUserUUID());
            } catch (Exception e) {
                System.err.println("Greška u rollback-u HR MS: " + event.getUserUUID());
            }
        }
    }
}
