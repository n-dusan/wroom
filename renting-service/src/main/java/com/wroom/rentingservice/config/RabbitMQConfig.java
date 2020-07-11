package com.wroom.rentingservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
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
    public static final String ROUTING_KEY = "tips";
    public static final String GPS_ROUTING_KEY = "gps_key";
    public static final String MAIL_QUEUE_NAME = "mail";
    public static final String GPS_QUEUE_NAME = "gps";

    @Bean
    public Queue mail_queue() {
        return new Queue(MAIL_QUEUE_NAME, false);
    }

    @Bean
    public Queue gps_queue() {
        return new Queue(GPS_QUEUE_NAME, false);
    }

    @Bean
    public TopicExchange tipsExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }


//    @Bean
//    public Binding queueToExchangeBinding() {
//        return BindingBuilder.bind(replicate_queue()).to(tipsExchange()).with(AUTH_ROUTING_KEY);
//    }

    @Bean
    List<Binding> bindings() {
        return Arrays.asList(BindingBuilder.bind(mail_queue()).to(tipsExchange()).with(ROUTING_KEY),
                BindingBuilder.bind(gps_queue()).to(tipsExchange()).with(GPS_ROUTING_KEY));
    }

    @Bean(name = "messageConverter")
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    //
    @Bean(name = "emailConnectionFactory")
//    @Primary
    public ConnectionFactory connectionFactoryEmail() {
        String uri = System.getenv("CLOUDAMQP_URL");
        if (uri == null) {
            //uri = "amqp://guest:guest@localhost";
            //for mail testing, use this URL, once you're done, test it with docker and remove it, since it's a secret property
            uri = "amqp://faihpmmd:2uO5YyUIoPZ0U7ybj4c1CA02odsiAO6r@squid.rmq.cloudamqp.com/faihpmmd";
        }
        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory();
        connectionFactory.setUri(uri);
        return connectionFactory;
    }


//    @Bean
//    public ConnectionFactory connectionFactoryReplicate() {
//        String uri = System.getenv("RMQ_HOST");
//        System.out.println("My uri what is it?" + uri);
//        if (uri == null) {
//            uri = "amqp://guest:guest@localhost";
//        }
//        else {
//            uri = "amqp://guest:guest@" + uri;
//        }
//        CachingConnectionFactory connectionFactory =
//                new CachingConnectionFactory();
//        connectionFactory.setUri(uri);
//        return connectionFactory;
//    }


    @Bean(name = "rabbitTemplateEmail")
//    @Primary
    public RabbitTemplate rabbitTemplateEmail() {

        RabbitTemplate rabbitTemplate = new RabbitTemplate(this.connectionFactoryEmail());
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

//    @Bean
////    @Primary
//    public RabbitTemplate rabbitTemplateReplicate(ConnectionFactory connectionFactory) {
//
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMessageConverter(messageConverter());
//        return rabbitTemplate;
//    }
}