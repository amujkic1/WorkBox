package com.example.auth.messaging;

import com.example.auth.config.RabbitMQConfig;
import com.example.auth.events.UserCreateConfirmedEvent;
import com.example.auth.events.UserCreateRollbackEvent;
import com.example.auth.events.UserCreatedEvent;
import com.example.auth.model.Role;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserEventProducer {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public UserEventProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendUserCreatedEvent(UserCreatedEvent event) {
        String routingKey = determineRoutingKey(Role.valueOf(event.getRole()));

        rabbitTemplate.convertAndSend(RabbitMQConfig.USER_EVENT_EXCHANGE, routingKey, event);
    }

    private String determineRoutingKey(Role role) {
        switch (role) {
            case HR:
                return "hr.user.created";
            case BUSINESS_MANAGER:
                return "business.manager.user.created";
            case FINANCE_MANAGER:
                return "finance.manager.user.created";
            default:
                return "user.management";
        }
    }
}


/*@Service
public class UserEventProducer {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public UserEventProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendUserCreatedEvent(UserCreatedEvent event) {
        try {
            rabbitTemplate.convertAndSend(RabbitMQConfig.USER_EVENT_EXCHANGE, "user.created", event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendUserCreateRollbackEvent(UserCreateRollbackEvent event) {
        try {
            rabbitTemplate.convertAndSend(RabbitMQConfig.USER_EVENT_EXCHANGE, "user.create.rollback", event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendUserCreateConfirmedEvent(UserCreateConfirmedEvent event) {
        try {
            rabbitTemplate.convertAndSend(RabbitMQConfig.USER_EVENT_EXCHANGE, "user.create.confirmed", event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}*/