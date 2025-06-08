package com.example.hr.config;

import com.example.hr.dto.UserDTO2;
import com.example.hr.model.User;
import com.example.hr.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

@Service
public class RabbitMQConsumer {

    private final UserService userService;

    public RabbitMQConsumer(UserService userService) {
        this.userService = userService;
    }


    @RabbitListener(queues = "user-registration-hr")
    public void consumeJSON(UserDTO2 userDTO){
        System.out.println("\n----------------");
        System.out.println("Primljena poruka je: "+userDTO);

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

    }
}
