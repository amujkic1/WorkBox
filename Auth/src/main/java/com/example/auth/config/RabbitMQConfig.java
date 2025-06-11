package com.example.auth.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    private String registrationQueue1 = "user-registration-finance";
    private String registrationQueue2 = "user-registration-hr";
    private String registrationQueue3 = "user-registration-business";

    private String exchange="auth-registration";

    private String routingJSONKey="registration";


    @Bean
    public Queue queueFinance() {
        return new Queue(registrationQueue1);
    }

    @Bean
    public Queue queueHR() {
        return new Queue(registrationQueue2);
    }

    @Bean
    public Queue queueBusiness() {
        return new Queue(registrationQueue3);
    }

    // Queue za status događaje (rollback)
    //@Bean
    //public Queue statusQueue() {
    //    return new Queue("user-registration-status", true);
    //}

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding bindingFinance() {
        return BindingBuilder
                .bind(queueFinance())
                .to(exchange())
                .with(routingJSONKey);
    }

    @Bean
    public Binding bindingHR() {
        return BindingBuilder
                .bind(queueHR())
                .to(exchange())
                .with(routingJSONKey);
    }

    @Bean
    public Binding bindingBusiness() {
        return BindingBuilder
                .bind(queueBusiness())
                .to(exchange())
                .with(routingJSONKey);
    }

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }





    @Bean
    public FanoutExchange registrationStatusFanoutExchange() {
        return new FanoutExchange("registration.status.exchange");
    }


    @Bean
    public Queue authStatusQueue() {
        return new Queue("user-registration-status.auth", true); // Jedinstveni red za AUTH
    }

    @Bean
    public Binding authStatusBinding() {
        return BindingBuilder
                .bind(authStatusQueue())
                .to(registrationStatusFanoutExchange());
    }




}
