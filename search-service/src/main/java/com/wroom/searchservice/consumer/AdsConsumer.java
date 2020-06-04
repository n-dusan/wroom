package com.wroom.searchservice.consumer;

import com.wroom.searchservice.config.RabbitMQConfig;
import com.wroom.searchservice.consumer.message.AdsMessage;
import com.wroom.searchservice.consumer.message.MailMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class AdsConsumer {

    //todo: crudovi ads, price-list, location
    @RabbitListener(queues = RabbitMQConfig.ADS_QUEUE_NAME)
    public void adsListen(AdsMessage mailMessage) {
        log.info("Got a message" + mailMessage);
    }
}
