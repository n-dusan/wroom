package wroom.authservice.producer;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import wroom.authservice.config.RabbitMQConfig;
import wroom.authservice.producer.messages.UserMessage;

@Component
@Log4j2
public class UserProducer {


    private final RabbitTemplate rabbitTemplate;

    public UserProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void send() {
        log.info("Sending a message>>> ... ");
        UserMessage userMessage = new UserMessage(1L, "Hi", "hello", "test", true, true);

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.AUTH_ROUTING_KEY, userMessage);
    }
}
