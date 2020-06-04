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

    //prima CRUDove: bodytype, brandtype, modeltype, gearboxtype, fueltype, vehicle
    @RabbitListener(queues = RabbitMQConfig.VEHICLE_QUEUE_NAME)
    public void vehicleListen(VehicleMessage message) {
        log.info("Received message > "+ message);

        if(message.getOperation() == OperationEnum.CREATE) {
            log.info("CREATE regime");
        } else if(message.getOperation() == OperationEnum.UPDATE) {
            log.info("UPDATE regime");
        } else if(message.getOperation() == OperationEnum.DELETE) {
            log.info("DELETE regime");
        }
    }


    //todo: crudovi ads, price-list, location
    @RabbitListener(queues = RabbitMQConfig.ADS_QUEUE_NAME)
    public void adsListen() {

    }
}
