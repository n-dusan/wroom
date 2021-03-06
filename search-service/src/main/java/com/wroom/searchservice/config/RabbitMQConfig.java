package com.wroom.searchservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "tips_tx";
    public static final String VEHICLE_ROUTING_KEY = "vehicle_key";
    public static final String AUTH_ROUTING_KEY = "auth_key";
    public static final String ADS_ROUTING_KEY = "ads_key";
    public static final String VEHICLE_QUEUE_NAME = "vehicle";
    public static final String ADS_QUEUE_NAME = "ads";
    public static final String AUTH_QUEUE_NAME = "auth";

    @Bean
    public Queue vehicle_queue() {
        return new Queue(VEHICLE_QUEUE_NAME, false);
    }

    @Bean
    public Queue ads_queue() {
        return new Queue(ADS_QUEUE_NAME, false);
    }

    @Bean
    public Queue auth_queue() {
        return new Queue(AUTH_QUEUE_NAME, false);
    }

    @Bean
    public TopicExchange tipsExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }


    @Bean
    List<Binding> bindings() {
        return Arrays.asList(BindingBuilder.bind(vehicle_queue()).to(tipsExchange()).with(VEHICLE_ROUTING_KEY),
                BindingBuilder.bind(ads_queue()).to(tipsExchange()).with(ADS_ROUTING_KEY),
                BindingBuilder.bind(auth_queue()).to(tipsExchange()).with(AUTH_ROUTING_KEY));
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}