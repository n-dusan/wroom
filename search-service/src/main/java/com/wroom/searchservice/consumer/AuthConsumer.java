package com.wroom.searchservice.consumer;

import com.wroom.searchservice.config.RabbitMQConfig;
import com.wroom.searchservice.consumer.message.AdsMessage;
import com.wroom.searchservice.consumer.message.MailMessage;
import com.wroom.searchservice.consumer.message.UserMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class AuthConsumer {

    @RabbitListener(queues = RabbitMQConfig.AUTH_QUEUE_NAME)
    public void authListen(UserMessage message) {
        log.info("Recieved message" + message);
    }
}
