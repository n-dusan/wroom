package com.wroom.searchservice.consumer;

import com.wroom.searchservice.config.RabbitMQConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class VehicleConsumer {

    @RabbitListener(queues = RabbitMQConfig.VEHICLE_QUEUE_NAME)
    public void listen(VehicleMessage message) {
        log.info("Received message > "+ message);
    }
}
