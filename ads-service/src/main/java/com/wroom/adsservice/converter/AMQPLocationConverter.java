package com.wroom.adsservice.converter;

import com.wroom.adsservice.domain.dto.LocationDTO;
import com.wroom.adsservice.producer.messages.AdsMessage;
import com.wroom.adsservice.producer.messages.EntityEnum;
import com.wroom.adsservice.producer.messages.LocationMessage;
import com.wroom.adsservice.producer.messages.OperationEnum;

public class AMQPLocationConverter {

	public static AdsMessage toAdsMessage(LocationDTO dto, OperationEnum operation) {
		AdsMessage message = new AdsMessage();
		message.setLocation(toLocationMessage(dto));
		message.setEntity(EntityEnum.LOCATION);
		message.setOperation(operation);
		
		return message;
	}
	
	public static LocationMessage toLocationMessage(LocationDTO dto) {
		LocationMessage message = new LocationMessage();
		message.setId(dto.getId());
		message.setCountry(dto.getCountry());
		message.setCity(dto.getCity());
		return message;
	}
	
}
