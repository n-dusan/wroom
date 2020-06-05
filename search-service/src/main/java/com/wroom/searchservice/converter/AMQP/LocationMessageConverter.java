package com.wroom.searchservice.converter.AMQP;

import com.wroom.searchservice.consumer.message.LocationMessage;
import com.wroom.searchservice.domain.dto.LocationDTO;

public class LocationMessageConverter {

	public static LocationDTO fromMessage(LocationMessage message) {
		LocationDTO dto = new LocationDTO();
		dto.setId(message.getId());
		dto.setCountry(message.getCountry());
		dto.setCity(message.getCity());
		return dto;
	}
	
}
