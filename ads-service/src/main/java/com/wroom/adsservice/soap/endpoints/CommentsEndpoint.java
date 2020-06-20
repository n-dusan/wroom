package com.wroom.adsservice.soap.endpoints;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.wroom.adsservice.soap.xsd.CommentRequest;
import com.wroom.adsservice.soap.xsd.CommentResponse;

@Endpoint
public class CommentsEndpoint {

	private static final String NAMESPACE_URI ="http://ftn.com/wroom-agent/xsd";

	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "CommentRequest")
	@ResponsePayload
	public CommentResponse sendMessageToService(@RequestPayload CommentRequest request) {
		System.out.println(">>>>>>>>>>> Received a Comment!");
		
		CommentResponse response = new CommentResponse();
//		response.setMessage(MessagesConverter.toSoapMessage(this.messageRepository.save(MessagesConverter.fromSoapMessage(request.getMessage()))));
		
		System.out.println(">>>>>>>>>>> Comment saved!");
		
		return response;
	}
	
}
