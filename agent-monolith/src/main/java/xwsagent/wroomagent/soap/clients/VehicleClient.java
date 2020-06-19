package xwsagent.wroomagent.soap.clients;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import xwsagent.wroomagent.domain.Vehicle;
import xwsagent.wroomagent.soap.converters.VehicleSoapConverter;
import xwsagent.wroomagent.soap.xsd.SendVehicleRequest;
import xwsagent.wroomagent.soap.xsd.SendVehicleResponse;

public class VehicleClient extends WebServiceGatewaySupport{

	public SendVehicleResponse send(Vehicle entity) {
		SendVehicleRequest request = new SendVehicleRequest();
		request.setVehicle(VehicleSoapConverter.toVehicleSoap(entity));
		
		System.out.println(">>>>>> Sening vehicle to wroom");
		SendVehicleResponse response = (SendVehicleResponse) getWebServiceTemplate().marshalSendAndReceive(request);
		System.out.println(">>>>>> Sent");
		
		return response;
	}
	
}
