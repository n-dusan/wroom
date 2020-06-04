package com.wroom.searchservice.converter.AMQP;

import com.wroom.searchservice.consumer.message.FeatureMessage;
import com.wroom.searchservice.domain.dto.FeatureDTO;

public class FeatureMessageConverter {

	public static FeatureDTO fromMessage(FeatureMessage message) {
		FeatureDTO dto = new FeatureDTO();
		dto.setId(message.getId());
		dto.setName(message.getName());
		if(message.getBrandId() != null) {
			dto.setBrandId(message.getBrandId());
		}
		return dto;
	}
	
}
