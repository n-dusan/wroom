package com.wroom.vehicleservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "tips_tx";
    public static final String GPS_EXCHANGE_NAME = "gps_tx";
    public static final String VEHICLE_ROUTING_KEY = "vehicle_key";
    public static final String GPS_ROUTING_KEY = "gps_key";
    public static final String VEHICLE_QUEUE_NAME = "vehicle";
    public static final String GPS_QUEUE_NAME = "gps";

    @Bean
    public Queue queue() {
        return new Queue(VEHICLE_QUEUE_NAME, false);
    }

    @Bean
    public Queue gps_queue() {
        return new Queue(GPS_QUEUE_NAME, false);
    }

    @Bean
    public TopicExchange tipsExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public TopicExchange gpsExchange() {
        return new TopicExchange(GPS_EXCHANGE_NAME);
    }

    @Bean
    public Binding queueToExchangeBinding() {
        return BindingBuilder.bind(queue()).to(tipsExchange()).with(VEHICLE_ROUTING_KEY);
    }

    @Bean
    public Binding gpsBinding() {
        return BindingBuilder.bind(gps_queue()).to(gpsExchange()).with(GPS_ROUTING_KEY);
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
