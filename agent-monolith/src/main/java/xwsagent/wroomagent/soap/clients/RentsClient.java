package xwsagent.wroomagent.soap.clients;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import xwsagent.wroomagent.domain.RentRequest;
import xwsagent.wroomagent.soap.converters.RentRequestSoapConverter;
import xwsagent.wroomagent.soap.xsd.SendRentRequest;
import xwsagent.wroomagent.soap.xsd.SendRentResponse;

public class RentsClient extends WebServiceGatewaySupport {

	public SendRentResponse send(RentRequest entity) {
		SendRentRequest request = new SendRentRequest();
		request.setRentRequest(RentRequestSoapConverter.toSoapRequest(entity));
		
		System.out.println(">>>>>> Sening rent to wroom");
		SendRentResponse response = (SendRentResponse) getWebServiceTemplate().marshalSendAndReceive(request);
		System.out.println(">>>>>> Sent");
		
		return response;
	}
	
}
