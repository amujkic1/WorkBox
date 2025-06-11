package com.example.hr.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public Queue userRegistrationQueue() {
        return new Queue("user-registration-hr", true);
    }

    //@Bean
    //public Queue statusQueue() {
    //    return new Queue("user-registration-status", true);
    //}

    //@Bean
    //public TopicExchange statusExchange() {
    //    return new TopicExchange("registration-status");
    //}

    //@Bean
    //public Binding statusBinding() {
    //    return BindingBuilder
    //           .bind(statusQueue())
    //            .to(statusExchange())
    //            .with("status");
    //}


    @Bean
    public Queue hrStatusQueue() {
        return new Queue("user-registration-status.hr", true);
    }

    @Bean
    public FanoutExchange registrationStatusFanoutExchange() {
        return new FanoutExchange("registration.status.exchange");
    }

    @Bean
    public Binding hrStatusBinding() {
        return BindingBuilder
                .bind(hrStatusQueue())
                .to(registrationStatusFanoutExchange());
    }
}
