package com.wroom.searchservice.converter.AMQP;

import com.wroom.searchservice.consumer.message.VehicleMessage;
import com.wroom.searchservice.domain.dto.VehicleDTO;

public class VehicleMessageConverter {

	public static VehicleDTO fromMessage(VehicleMessage message) {
		VehicleDTO dto = new VehicleDTO();
		dto.setId(message.getId());
		dto.setMileage(message.getMileage());
		dto.setChildSeats(message.getChildSeats());
		dto.setCdw(message.getCdw());
		dto.setModelType(FeatureMessageConverter.fromMessage(message.getModelType()));
		dto.setBrandType(FeatureMessageConverter.fromMessage(message.getBrandType()));
		dto.setBodyType(FeatureMessageConverter.fromMessage(message.getBodyType()));
		dto.setFuelType(FeatureMessageConverter.fromMessage(message.getFuelType()));
		dto.setGearboxType(FeatureMessageConverter.fromMessage(message.getGearboxType()));
		dto.setOwnerId(message.getOwnerId());
		
		return dto;
	}
	
}
