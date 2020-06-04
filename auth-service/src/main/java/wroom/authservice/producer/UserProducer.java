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

    @Autowired
    @Qualifier("rabbitTemplateReplicate") // Needed when want to use the non-primary bean
    private RabbitTemplate rabbitTemplate;

    public void send() {
        log.info("Sending a message>>> ... ");
        UserMessage userMessage = new UserMessage("testboy@maildrop.cc,", "Hello!", "Yes, my child.", "test");

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.AUTH_ROUTING_KEY, userMessage);
        log.info("Sent a message to >"+userMessage.getRecipient());
    }
}
