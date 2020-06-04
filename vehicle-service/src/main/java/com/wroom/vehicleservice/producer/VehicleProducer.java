package com.wroom.vehicleservice.producer;

import com.wroom.vehicleservice.config.RabbitMQConfig;

import com.wroom.vehicleservice.producer.message.VehicleMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class VehicleProducer {

    private static final String LOG_SEND = "action=replicate-search-data operation=%s entity=%s";

    private final RabbitTemplate rabbitTemplate;

    public VehicleProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(VehicleMessage message) {
        String logContent = String.format(LOG_SEND, message.getOperation().toString(), message.getEntity().toString());
        log.info(logContent);

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, message);
    }
}
