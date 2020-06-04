package com.wroom.adsservice.converter;

import com.wroom.adsservice.domain.dto.AdDTO;
import com.wroom.adsservice.producer.messages.AdsMessage;
import com.wroom.adsservice.producer.messages.OperationEnum;

public class AMQPAdConverter {

	public static AdsMessage toAdsMessage(AdDTO dto, OperationEnum operation) {
		AdsMessage message = new AdsMessage();
		// TODO: Fun fun fun
		return message;
	}
	
}
