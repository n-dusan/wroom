package com.wroom.searchservice.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.wroom.searchservice.config.RabbitMQConfig;
import com.wroom.searchservice.consumer.message.AdsMessage;
import com.wroom.searchservice.consumer.message.OperationEnum;
import com.wroom.searchservice.consumer.message.handlers.AdsMessageHandler;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class AdsConsumer {

	private final AdsMessageHandler adsMessageHandler;
	
	public AdsConsumer(AdsMessageHandler adsMessageHandler) {
		this.adsMessageHandler = adsMessageHandler;
	}
	
    @RabbitListener(queues = RabbitMQConfig.ADS_QUEUE_NAME)
    public void adsListen(AdsMessage message) {
        log.info("Got a message" + message);
        
        if(message.getOperation() == OperationEnum.CREATE) {
        	log.info("CREATE regime");
        	this.adsMessageHandler.createEntity(message);
        } else if(message.getOperation() == OperationEnum.UPDATE) {
        	log.info("UPDATE regime");
        	this.adsMessageHandler.updateEntity(message);
        } else if(message.getOperation() == OperationEnum.DELETE) {
        	log.info("DELETE regime");
        	this.adsMessageHandler.deleteEntity(message);
        }
    }
}
