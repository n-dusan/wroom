package com.wroom.adsservice.converter;

import com.wroom.adsservice.domain.dto.AdDTO;
import com.wroom.adsservice.producer.messages.AdsMessage;
import com.wroom.adsservice.producer.messages.EntityEnum;
import com.wroom.adsservice.producer.messages.OperationEnum;

public class AMQPAdConverter {

	public static AdsMessage toAdsMessage(AdDTO dto, OperationEnum operation) {
		AdsMessage message = new AdsMessage();
		message.setId(dto.getId());
		message.setPublishDate(dto.getAvailableFrom());	// hehe
		message.setAvailableFrom(dto.getAvailableFrom());
		message.setAvailableTo(dto.getAvailableTo());
		message.setVehicleId(dto.getVehicleId());
		message.setMileLimit(dto.getMileLimit());
		message.setMileLimitEnabled(dto.isMileLimitEnabled());
		message.setAddress(dto.getAddress());
		message.setGps(dto.isGps());
		
//		message.setPriceList(priceList);	staviti u servisu odakle se ova metoda poziva
//		message.setLocation(location); 		i ovo staviti u servisu jer ID
		
		message.setEntity(EntityEnum.AD);
		message.setOperation(operation);
		return message;
	}
	
}
