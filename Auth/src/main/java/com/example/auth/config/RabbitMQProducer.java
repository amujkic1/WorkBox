package com.example.auth.config;

import com.example.auth.dto.UserDTO;
import com.example.auth.model.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    private String exchange = "auth-registration";

    private String routingJSONKey ="registration";

    private RabbitTemplate rabbitTemplate;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void sendJSONMessage(UserDTO user){

        System.out.println("Poslat je: "+ user);
        rabbitTemplate.convertAndSend(exchange, routingJSONKey, user);
        System.out.println("\n\n----------------------------");
    }
}
