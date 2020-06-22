package com.wroom.rentingservice.soap.converters;

import com.wroom.rentingservice.converter.AbstractConverter;
import com.wroom.rentingservice.soap.xsd.Message;

public class MessagesConverter extends AbstractConverter {

	public static com.wroom.rentingservice.domain.Message fromSoapMessage(com.wroom.rentingservice.soap.xsd.Message soap) {
		return new com.wroom.rentingservice.domain.Message(
				null,
				null,
				null,
				soap.getRentRequestId(),
				soap.getTitle(),
				soap.getContent(),
				soap.getDate(),
				soap.getFromUser(),
				soap.getToUser(),
				soap.getId() == null ? null : soap.getId()
		);
	}
	 
	public static Message toSoapMessage(com.wroom.rentingservice.domain.Message entity) {
		com.wroom.rentingservice.soap.xsd.Message ret = new Message();
		ret.setContent(entity.getContent());
		ret.setTitle(entity.getTitle());
		ret.setDate(entity.getDate());
		ret.setId(entity.getId());
		ret.setFromUser(entity.getFromUser());
		ret.setToUser(entity.getToUser());
		ret.setRentRequestId(entity.getRentRequestId());
		return ret;
	}
	
}
