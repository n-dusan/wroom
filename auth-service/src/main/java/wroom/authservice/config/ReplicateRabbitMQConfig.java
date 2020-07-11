package wroom.authservice.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ReplicateRabbitMQConfig {

    @Autowired
    RabbitMQConfig rabbitMQConfig;

    @Value("${spring.rabbitmq.host}")
    protected String host;

    @Value("${spring.rabbitmq.port}")
    protected String port;

    @Value("${spring.rabbitmq.username}")
    protected String username;

    @Value("${spring.rabbitmq.password}")
    protected String password;


    @Bean
    @Primary
    public ConnectionFactory connectionFactory() {

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(Integer.parseInt(port));
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }


    @Bean(name = "rabbitTemplateReplicate")
    @Primary
    public RabbitTemplate rabbitTemplateReplicate() {

        RabbitTemplate rabbitTemplate = new RabbitTemplate(this.connectionFactory());
        rabbitTemplate.setMessageConverter(rabbitMQConfig.messageConverter());
        return rabbitTemplate;
    }
}
