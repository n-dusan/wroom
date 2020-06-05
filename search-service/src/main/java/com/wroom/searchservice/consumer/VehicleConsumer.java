package com.wroom.searchservice.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.wroom.searchservice.config.RabbitMQConfig;
import com.wroom.searchservice.consumer.message.OperationEnum;
import com.wroom.searchservice.consumer.message.VehicleMessage;
import com.wroom.searchservice.service.AdService;
import com.wroom.searchservice.service.VehicleService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class VehicleConsumer {

	private final VehicleMessageHandler vehicleMessageHandler;
	
    public VehicleConsumer(VehicleMessageHandler vehicleMessageHandler) {
		this.vehicleMessageHandler = vehicleMessageHandler;
	}


	//prima CRUDove: bodytype, brandtype, modeltype, gearboxtype, fueltype, vehicle
    @RabbitListener(queues = RabbitMQConfig.VEHICLE_QUEUE_NAME)
    public void vehicleListen(VehicleMessage message) {
        log.info("Received message > "+ message);

        if(message.getOperation() == OperationEnum.CREATE) {

        	this.vehicleMessageHandler.createEntity(message);

        } else if(message.getOperation() == OperationEnum.UPDATE) {

            this.vehicleMessageHandler.updateEntity(message);

        } else if(message.getOperation() == OperationEnum.DELETE) {

            this.vehicleMessageHandler.deleteEntity(message);
        }
    }



}
