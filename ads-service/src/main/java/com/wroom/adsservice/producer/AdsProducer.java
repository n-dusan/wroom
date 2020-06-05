package com.wroom.adsservice.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.wroom.adsservice.config.RabbitMQConfig;
import com.wroom.adsservice.producer.messages.AdsMessage;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class AdsProducer {

	private static final String LOG_SEND = "action=replicate-search-data operation=%s entity=%s";

	private final RabbitTemplate rabbitTemplate;

	public AdsProducer(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}


	public void send(AdsMessage message) {
		String logContent = String.format(LOG_SEND, message.getOperation().toString(), message.getEntity().toString());
		log.info(logContent);
		rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, message);
	}

}
