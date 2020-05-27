package wroom.authservice.config;

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

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "tips_tx";
    public static final String DEFAULT_PARSING_QUEUE = "default_parser_q";
    public static final String ROUTING_KEY = "tips";
    public static final String QUEUE_NAME = "mail";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    public TopicExchange tipsExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding queueToExchangeBinding() {
        return BindingBuilder.bind(queue()).to(tipsExchange()).with(ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
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


    @Bean
    public RabbitTemplate rabbitTemplate() {

        RabbitTemplate rabbitTemplate = new RabbitTemplate(this.connectionFactory());
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
