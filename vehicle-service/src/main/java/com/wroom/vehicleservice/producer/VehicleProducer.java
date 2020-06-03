package com.wroom.vehicleservice.producer;

import com.wroom.vehicleservice.config.RabbitMQConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class VehicleProducer {

    private final RabbitTemplate rabbitTemplate;

    public VehicleProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send() {
        log.info("Sending a feature message");
        FeatureMessage featureMessage = new FeatureMessage(1L, "Test");
        VehicleMessage v = new VehicleMessage(1L, 23.5, 4, false, featureMessage, featureMessage, featureMessage, featureMessage, featureMessage);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, v);
        log.info("Sent " + v.getMileage());
    }
}
