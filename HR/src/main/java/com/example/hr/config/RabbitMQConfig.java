package com.example.hr.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

@Configuration
public class RabbitMQConfig {

    public static final String USER_EVENT_EXCHANGE = "user.event.exchange";
    public static final String USER_CREATED_QUEUE = "user.created.queue";

    @Bean
    public Queue userCreatedQueue() {
        return new Queue(USER_CREATED_QUEUE, true);
    }

    @Bean
    public TopicExchange userEventExchange() {
        return new TopicExchange(USER_EVENT_EXCHANGE);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        //CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("rabbitmq");
        connectionFactory.setUsername("guest");  // Korisničko ime za RabbitMQ
        connectionFactory.setPassword("guest");  // Lozinka za RabbitMQ
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory()); // Postavite ConnectionFactory
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());  // Postavite JSON converter
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Binding bindingUserCreated(Queue userCreatedQueue, TopicExchange userEventExchange) {
        return BindingBuilder.bind(userCreatedQueue).to(userEventExchange).with("user.created");
    }
}
