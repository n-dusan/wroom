package com.wroom.searchservice.converter.AMQP;

import com.wroom.searchservice.consumer.message.PricelistMessage;
import com.wroom.searchservice.domain.dto.PriceListDTO;

public class PriceListMessageConverter {

	public static PriceListDTO fromMessage(PricelistMessage message) {
		PriceListDTO dto = new PriceListDTO();
		dto.setId(message.getId());
		dto.setPricePerDay(message.getPricePerDay());
		dto.setPricePerMile(message.getPricePerMile());
		dto.setDiscount(message.getDiscount());
		dto.setPriceCDW(message.getPriceCDW());
		// Set user from another place
		return dto;
	}
	
}
