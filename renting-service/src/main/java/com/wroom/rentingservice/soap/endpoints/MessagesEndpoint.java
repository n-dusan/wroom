package com.wroom.rentingservice.soap.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.wroom.rentingservice.repository.MessageRepository;
import com.wroom.rentingservice.soap.converters.MessagesConverter;
import com.wroom.rentingservice.soap.xsd.SendMessageRequest;
import com.wroom.rentingservice.soap.xsd.SendMessageResponse;

@Endpoint
public class MessagesEndpoint {

	private static final String NAMESPACE_URI ="http://ftn.com/renting-service/xsd";

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	public MessagesEndpoint(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}

	/**
	 * Method invoked by monolith app, when responding to messages
	 * MONOLITH ---> MICROSERVICE
	 * @param request
	 * @return
	 */
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "SendMessageRequest")
	@ResponsePayload
	public SendMessageResponse sendMessageToService(@RequestPayload SendMessageRequest request) {
		System.out.println(">>>>>>>>>>> Received a Message!");
		
		SendMessageResponse response = new SendMessageResponse();
		response.setMessage(MessagesConverter.toSoapMessage(this.messageRepository.save(MessagesConverter.fromSoapMessage(request.getMessage()))));
		System.out.println(">>>>>>>>>>> Message saved!");
		
		return response;
	}
	
}
