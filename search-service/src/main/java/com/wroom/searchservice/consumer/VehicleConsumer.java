package com.wroom.searchservice.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.wroom.searchservice.config.RabbitMQConfig;
import com.wroom.searchservice.consumer.message.OperationEnum;
import com.wroom.searchservice.consumer.message.VehicleMessage;
import com.wroom.searchservice.consumer.message.handlers.VehicleMessageHandler;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class VehicleConsumer {

	private final VehicleMessageHandler vehicleMessageHandler;
	
    public VehicleConsumer(VehicleMessageHandler vehicleMessageHandler) {
		this.vehicleMessageHandler = vehicleMessageHandler;
	}

    @RabbitListener(queues = RabbitMQConfig.VEHICLE_QUEUE_NAME)
    public void vehicleListen(VehicleMessage message) {
        log.info("Received message > "+ message);

        if(message.getOperation() == OperationEnum.CREATE) {
        	log.info("CREATE regime");
        	this.vehicleMessageHandler.createEntity(message);
        } else if(message.getOperation() == OperationEnum.UPDATE) {
            log.info("UPDATE regime");
            this.vehicleMessageHandler.updateEntity(message);
        } else if(message.getOperation() == OperationEnum.DELETE) {
            log.info("DELETE regime");
            this.vehicleMessageHandler.deleteEntity(message);
        }
    }



}
