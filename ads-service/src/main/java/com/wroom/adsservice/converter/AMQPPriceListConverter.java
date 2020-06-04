package com.wroom.adsservice.converter;

import com.wroom.adsservice.domain.dto.LocationDTO;
import com.wroom.adsservice.domain.dto.PriceListDTO;
import com.wroom.adsservice.producer.messages.AdsMessage;
import com.wroom.adsservice.producer.messages.EntityEnum;
import com.wroom.adsservice.producer.messages.OperationEnum;
import com.wroom.adsservice.producer.messages.PricelistMessage;

public class AMQPPriceListConverter {

	public static AdsMessage toAdsMessage(PriceListDTO dto, OperationEnum operation) {
		AdsMessage message = new AdsMessage();
		message.setPriceList(toPriceListMessage(dto));
		message.setEntity(EntityEnum.PRICELIST);
		message.setOperation(operation);
		
		return message;
	}
	
	public static PricelistMessage toPriceListMessage(PriceListDTO dto) {
		PricelistMessage message = new PricelistMessage();
		message.setId(dto.getId());
		message.setPricePerDay(dto.getPricePerDay());
		message.setPricePerMile(dto.getPricePerMile());
		message.setPriceCDW(dto.getPriceCDW());
		message.setDiscount(dto.getDiscount());
		message.setUserId(dto.getUserId());
		return message;
	}
	
}
