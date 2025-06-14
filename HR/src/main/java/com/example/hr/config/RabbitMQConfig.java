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

    public static final String HR_USER_CREATED_QUEUE = "hr.user.created.queue";
    public static final String BUSINESS_MANAGER_USER_CREATED_QUEUE = "business.manager.user.created.queue";
    public static final String FINANCE_MANAGER_USER_CREATED_QUEUE = "finance.manager.user.created.queue";

    @Bean
    public Queue hrUserCreatedQueue() {
        return new Queue(HR_USER_CREATED_QUEUE, true);
    }

    @Bean
    public Queue businessManagerUserCreatedQueue() {
        return new Queue(BUSINESS_MANAGER_USER_CREATED_QUEUE, true);
    }

    @Bean
    public Queue financeManagerUserCreatedQueue() {
        return new Queue(FINANCE_MANAGER_USER_CREATED_QUEUE, true);
    }

    @Bean
    public TopicExchange userEventExchange() {
        return new TopicExchange(USER_EVENT_EXCHANGE);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("rabbitmq");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory());
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // Binding između queue-a i exchange-a
    @Bean
    public Binding bindingHRUserCreated(Queue hrUserCreatedQueue, TopicExchange userEventExchange) {
        return BindingBuilder.bind(hrUserCreatedQueue).to(userEventExchange).with("hr.user.created");
    }

    @Bean
    public Binding bindingBusinessManagerUserCreated(Queue businessManagerUserCreatedQueue, TopicExchange userEventExchange) {
        return BindingBuilder.bind(businessManagerUserCreatedQueue).to(userEventExchange).with("business.manager.user.created");
    }

    @Bean
    public Binding bindingFinanceManagerUserCreated(Queue financeManagerUserCreatedQueue, TopicExchange userEventExchange) {
        return BindingBuilder.bind(financeManagerUserCreatedQueue).to(userEventExchange).with("finance.manager.user.created");
    }
}


/*@Configuration
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
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("rabbitmq");
        //CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory());
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
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
}*/

/*package com.example.hr.config;

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
*/