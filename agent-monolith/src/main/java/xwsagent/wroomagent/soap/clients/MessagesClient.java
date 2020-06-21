package xwsagent.wroomagent.soap.clients;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import xwsagent.wroomagent.soap.converters.MessagesConverter;
import xwsagent.wroomagent.soap.xsd.SendMessageRequest;
import xwsagent.wroomagent.soap.xsd.SendMessageResponse;

public class MessagesClient extends WebServiceGatewaySupport {

	public SendMessageResponse send(xwsagent.wroomagent.domain.Message entity) {
		SendMessageRequest request = new SendMessageRequest();
		request.setMessage(MessagesConverter.toSoapMessage(entity));
		
		SendMessageResponse response = (SendMessageResponse) getWebServiceTemplate().marshalSendAndReceive(request);
		
		return response;
	}


	
}
