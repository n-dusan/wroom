package com.wroom.searchservice.converter.AMQP;

import com.wroom.searchservice.consumer.message.AdsMessage;
import com.wroom.searchservice.domain.dto.AdDTO;

public class AdsMessageConverter {
	
	public static AdDTO fromMessage(AdsMessage message) {
		AdDTO dto = new AdDTO();
		if(message.getId() !=null) {
			dto.setId(message.getId());
		}
		
		dto.setPublishDate(message.getPublishDate());
		dto.setAvailableFrom(message.getAvailableFrom());
		dto.setAvailableTo(message.getAvailableTo());
		dto.setMileLimit(message.getMileLimit());
		dto.setMileLimitEnabled(message.isMileLimitEnabled());
		dto.setGps(message.isGps());
		dto.setAddress(message.getAddress());
		dto.setVehicleId(message.getVehicleId());
		dto.setPriceListId(message.getPriceList().getId());
		dto.setLocationId(message.getLocation().getId());
		return dto;
	}
}
