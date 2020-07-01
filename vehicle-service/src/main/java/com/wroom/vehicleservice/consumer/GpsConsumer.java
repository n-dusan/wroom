package com.wroom.vehicleservice.consumer;

import com.wroom.vehicleservice.config.RabbitMQConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class GpsConsumer {

    private final SimpMessagingTemplate template;

    public GpsConsumer(SimpMessagingTemplate template) {
        this.template = template;
    }

    @RabbitListener(queues = RabbitMQConfig.GPS_QUEUE_NAME)
    public void gpsListen(GPSCoordinates message) {
        log.info("Recieved message" + message);
        this.template.convertAndSend("/gps", message);
    }
}
