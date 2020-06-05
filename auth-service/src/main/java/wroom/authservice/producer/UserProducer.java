package wroom.authservice.producer;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import wroom.authservice.config.RabbitMQConfig;
import wroom.authservice.producer.messages.UserMessage;


@Component
@Log4j2
public class UserProducer {

    private static final String LOG_SEND = "action=replicate-search-data operation=%s entity=USER";

    private final RabbitTemplate rabbitTemplate;

    public UserProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void send(UserMessage userMessage) {
        String logContent = String.format(LOG_SEND, userMessage.getOperation().toString());
        log.info(logContent);

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.AUTH_ROUTING_KEY, userMessage);
    }
}
