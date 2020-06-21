package xwsagent.wroomagent.soap.clients;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import xwsagent.wroomagent.domain.BundledRequests;
import xwsagent.wroomagent.domain.RentRequest;
import xwsagent.wroomagent.soap.converters.BundledRequestsSoapConverter;
import xwsagent.wroomagent.soap.converters.RentRequestSoapConverter;
import xwsagent.wroomagent.soap.xsd.*;

public class RentsClient extends WebServiceGatewaySupport {

	public SendRentResponse send(RentRequest entity, OperationRents operation) {
		SendRentRequest request = new SendRentRequest();
		request.setRentRequest(RentRequestSoapConverter.toSoapRequest(entity));
		request.setOperation(operation);
		
		System.out.println(">>>>>> Sening rent to wroom");
		SendRentResponse response = (SendRentResponse) getWebServiceTemplate().marshalSendAndReceive(request);
		System.out.println(">>>>>> Sent");
		
		return response;
	}
	
	public SendBundleResponse sendBundle(BundledRequests entity) {
		SendBundleRequest request = new SendBundleRequest();
		request.setBundle(BundledRequestsSoapConverter.toSoapBundle(entity));
		
		System.out.println(">>>>>> Sening bundle to wroom");
		SendBundleResponse response = (SendBundleResponse) getWebServiceTemplate().marshalSendAndReceive(request);
		System.out.println(">>>>>> Sent");
		
		return response;
	}


	public BundledRequestsListSoapResponse syncBundles() {

	}
	
}
