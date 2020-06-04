package com.wroom.adsservice.producer;

import com.wroom.adsservice.config.RabbitMQConfig;
import com.wroom.adsservice.producer.messages.AdsMessage;
import com.wroom.adsservice.producer.messages.MailMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class AdsProducer {

    private final RabbitTemplate rabbitTemplate;

    public AdsProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send() {
        //String logContent = String.format(LOG_SEND, message.getOperation().toString(), message.getEntity().toString());
        log.info("Sending to ads queue");
        AdsMessage message = new AdsMessage("test", "test", "Test");
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, message);
    }

}
