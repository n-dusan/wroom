package com.wroom.searchservice.consumer;

import com.wroom.searchservice.config.RabbitMQConfig;
import com.wroom.searchservice.consumer.message.AdsMessage;
import com.wroom.searchservice.consumer.message.MailMessage;
import com.wroom.searchservice.consumer.message.UserMessage;
import com.wroom.searchservice.consumer.message.UserOperationEnum;
import com.wroom.searchservice.converter.AMQP.UserMessageConverter;
import com.wroom.searchservice.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class AuthConsumer {

    private final UserService userService;

    public AuthConsumer(UserService userService) {
        this.userService = userService;
    }

    @RabbitListener(queues = RabbitMQConfig.AUTH_QUEUE_NAME)
    public void authListen(UserMessage message) {
        if(message.getOperation() == UserOperationEnum.ACTIVATE) {

            System.out.println("My message " + message);
            this.userService.registerAccount(UserMessageConverter.fromMessage(message));

        } else if(message.getOperation() == UserOperationEnum.LOCK) {

            this.userService.lockAccount(message.getId());

        } else if(message.getOperation() == UserOperationEnum.UNLOCK) {

            this.userService.unlockAccount(message.getId());

        } else if(message.getOperation() == UserOperationEnum.DELETE) {
            this.userService.delete(message.getId());
        }
        log.info("Recieved message" + message);
    }
}
