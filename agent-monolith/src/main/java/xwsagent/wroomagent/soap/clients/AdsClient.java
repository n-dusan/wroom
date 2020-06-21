package xwsagent.wroomagent.soap.clients;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import xwsagent.wroomagent.domain.Ad;
import xwsagent.wroomagent.soap.converters.AdSoapConverter;
import xwsagent.wroomagent.soap.xsd.Operation;
import xwsagent.wroomagent.soap.xsd.SendAdRequest;
import xwsagent.wroomagent.soap.xsd.SendAdResponse;

public class AdsClient extends WebServiceGatewaySupport{

	public SendAdResponse send(Ad entity, Operation operation) {
		SendAdRequest request = new SendAdRequest();
		request.setAd(AdSoapConverter.toAdSoap(entity));
		request.setOperation(operation);
		
		System.out.println(">>>>>> Sending ad to wroom");
		SendAdResponse response = (SendAdResponse) getWebServiceTemplate().marshalSendAndReceive(request);
		System.out.println(">>>>>> Sent");
		
		return response;
	}
	
}
