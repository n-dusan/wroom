package com.wroom.searchservice.consumer;

import com.wroom.searchservice.config.RabbitMQConfig;
import com.wroom.searchservice.consumer.message.OperationEnum;
import com.wroom.searchservice.consumer.message.VehicleMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class VehicleConsumer {

    @RabbitListener(queues = RabbitMQConfig.VEHICLE_QUEUE_NAME)
    public void listen(VehicleMessage message) {
        log.info("Received message > "+ message);
        if(message.getOperation() == null) {
            //faulty request, drop the message and log it (this is not a correct log format)
            log.error("Error with the received message");
            return;
        }

        if(message.getOperation() == OperationEnum.CREATE) {
            log.info("CREATE regime");
        } else if(message.getOperation() == OperationEnum.UPDATE) {
            log.info("UPDATE regime");
        } else if(message.getOperation() == OperationEnum.DELETE) {
            log.info("DELETE regime");
        }
    }
}
