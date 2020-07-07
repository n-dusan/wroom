package xwsagent.wroomagent.soap.clients;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import xwsagent.wroomagent.domain.Vehicle;
import xwsagent.wroomagent.domain.auth.User;
import xwsagent.wroomagent.repository.VehicleRepository;
import xwsagent.wroomagent.repository.rbac.UserRepository;
import xwsagent.wroomagent.soap.converters.VehicleSoapConverter;
import xwsagent.wroomagent.soap.xsd.*;

import java.util.List;

@Log4j2
public class VehicleClient extends WebServiceGatewaySupport {


	public static final String MONOLITH_USER_EMAIL = "zika@maildrop.cc";

	@Autowired
	private VehicleRepository vehicleRepository;

	@Autowired
	private UserRepository userRepository;

	public SendVehicleResponse send(Vehicle entity, Operation operation) {
		SendVehicleRequest request = new SendVehicleRequest();
		request.setVehicle(VehicleSoapConverter.toVehicleSoap(entity));
		request.setOperation(operation);
		
		log.info("action=send-vehicle status=started");
		SendVehicleResponse response = (SendVehicleResponse) getWebServiceTemplate().marshalSendAndReceive(request);
		log.info("action=send-vehicle status=ended");
		return response;
	}

	public void sync() {
		log.info("action=send-vehicles-sync status=started");

		SendVehicleListRequestResponse request = new SendVehicleListRequestResponse();

		User user = userRepository.findByEmail(MONOLITH_USER_EMAIL).get();
		List<Vehicle> vehicleList = vehicleRepository.findAllActiveForUser(user.getId());

		List<VehicleSoap> soapList = VehicleSoapConverter.toEntityList(vehicleList, VehicleSoapConverter::toVehicleSoap);

		request.setVehicle(soapList);
		request.setCompanyEmail(MONOLITH_USER_EMAIL);

		SendVehicleListRequestResponse response = (SendVehicleListRequestResponse) getWebServiceTemplate().marshalSendAndReceive(request);
		log.info("action=send-vehicles-sync status=ended");
	}
	
}
