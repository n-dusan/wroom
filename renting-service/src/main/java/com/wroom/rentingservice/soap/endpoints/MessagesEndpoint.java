package com.wroom.rentingservice.soap.endpoints;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.wroom.rentingservice.domain.Message;
import com.wroom.rentingservice.repository.MessageRepository;
import com.wroom.rentingservice.service.MessageService;
import com.wroom.rentingservice.soap.converters.MessagesConverter;
import com.wroom.rentingservice.soap.xsd.MessageListRequest;
import com.wroom.rentingservice.soap.xsd.MessageListResponse;
import com.wroom.rentingservice.soap.xsd.MessageUpdateRequest;
import com.wroom.rentingservice.soap.xsd.MessageUpdateResponse;
import com.wroom.rentingservice.soap.xsd.SendMessageRequest;
import com.wroom.rentingservice.soap.xsd.SendMessageResponse;

import lombok.extern.log4j.Log4j2;

@Endpoint
@Log4j2
public class MessagesEndpoint {

	private static final String NAMESPACE_URI ="http://ftn.com/wroom-agent/xsd";

	@Autowired
	private MessageRepository messageRepository;
	@Autowired
	private MessageService messageService;

	@Autowired
	public MessagesEndpoint(MessageRepository messageRepository, MessageService messageService) {
		this.messageRepository = messageRepository;
		this.messageService = messageService;
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
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "MessageListRequest")
	@ResponsePayload
	public MessageListResponse sync(@RequestPayload MessageListRequest request) {
		log.info("sync=messages action=started");

		List<Message> messageList = this.messageService.findMessagesByOwnerEmail(request.getCompanyEmail());

		MessageListResponse response = new MessageListResponse();
 
		List<Message> ret = new ArrayList<Message>();
		for(Message message : messageList) {
			if(message.getLocalId() != null) {
				continue;
			}
			ret.add(message);
		}

		List<com.wroom.rentingservice.soap.xsd.Message> soapList = MessagesConverter.toEntityList(ret, MessagesConverter::toSoapMessage);
		response.setMessages(soapList);

		log.info("sync=messages action=ended");
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "MessageUpdateRequest")
	@ResponsePayload
	public MessageUpdateResponse updateId(@RequestPayload MessageUpdateRequest request) {
		log.info("update=messages action=started");

		this.messageService.updateLocalId(request.getId(), request.getLocalId());

		MessageUpdateResponse response = new MessageUpdateResponse();
		response.setId(request.getId());
		response.setLocalId(request.getLocalId());

		log.info("update=messages action=ended");
		return response;
	}
	
}
