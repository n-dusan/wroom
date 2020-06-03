package com.wroom.vehicleservice.producer;

import com.wroom.vehicleservice.config.RabbitMQConfig;
import com.wroom.vehicleservice.producer.message.EntityEnum;
import com.wroom.vehicleservice.producer.message.FeatureMessage;
import com.wroom.vehicleservice.producer.message.OperationEnum;
import com.wroom.vehicleservice.producer.message.VehicleMessage;
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
        log.info("Sending a vehicle message");
        FeatureMessage featureMessage = new FeatureMessage(1L, "Test");
        VehicleMessage v1 = new VehicleMessage(
                1L,
                23.5,
                4,
                false,
                featureMessage,
                featureMessage,
                featureMessage,
                featureMessage,
                featureMessage,
                OperationEnum.CREATE,
                EntityEnum.VEHICLE);

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, v1);

        VehicleMessage v2 = new VehicleMessage(
                23.5,
                4,
                false,
                featureMessage,
                OperationEnum.UPDATE,
                EntityEnum.BODY_TYPE);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, v2);
        log.info("Sent " + v1.getId() + " " + v2.getMileage());
    }
}
