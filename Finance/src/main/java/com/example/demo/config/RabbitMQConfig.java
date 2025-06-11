package com.example.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
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
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter());
        return factory;
    }


    @Bean
    public Queue userRegistrationQueue() {
        return new Queue("user-registration-finance", true);
    }

    @Bean
    public TopicExchange authRegistrationExchange() {
        return new TopicExchange("auth-registration");
    }

    @Bean
    public Binding bindingFinanceRegistration() {
        return BindingBuilder
                .bind(userRegistrationQueue())  // "user-registration-finance"
                .to(authRegistrationExchange())
                .with("registration");
    }

    // Queue za status događaje (rollback)
    //@Bean
   // public Queue statusQueue() {
    //    return new Queue("user-registration-status", true);
    //}


    // Exchange za status događaje (različit od auth-registration!)
    //@Bean
    //public TopicExchange statusExchange() {
    //    return new TopicExchange("registration-status");
   // }

    // Binding za status poruke
    //@Bean
    //public Binding statusBinding() {
    //    return BindingBuilder
    //            .bind(statusQueue())
    //            .to(statusExchange())
    //            .with("status");
    //}



    @Bean
    public Queue financeStatusQueue() {
        return new Queue("user-registration-status.finance", true);
    }

    // Dodaj fanout exchange (isti kao kod auth i business)
    @Bean
    public FanoutExchange registrationStatusFanoutExchange() {
        return new FanoutExchange("registration.status.exchange");
    }

    // Poveži red sa fanout exchange-om
    @Bean
    public Binding financeStatusBinding() {
        return BindingBuilder
                .bind(financeStatusQueue())
                .to(registrationStatusFanoutExchange());
    }


}
