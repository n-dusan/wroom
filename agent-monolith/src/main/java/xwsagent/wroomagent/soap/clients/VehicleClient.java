package xwsagent.wroomagent.soap.clients;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.Base64;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import lombok.extern.log4j.Log4j2;
import xwsagent.wroomagent.domain.Image;
import xwsagent.wroomagent.domain.Vehicle;
import xwsagent.wroomagent.domain.auth.User;
import xwsagent.wroomagent.repository.ImageRepository;
import xwsagent.wroomagent.repository.VehicleRepository;
import xwsagent.wroomagent.repository.rbac.UserRepository;
import xwsagent.wroomagent.soap.converters.VehicleSoapConverter;
import xwsagent.wroomagent.soap.xsd.ImageSoap;
import xwsagent.wroomagent.soap.xsd.Operation;
import xwsagent.wroomagent.soap.xsd.SendImageRequest;
import xwsagent.wroomagent.soap.xsd.SendImageResponse;
import xwsagent.wroomagent.soap.xsd.SendVehicleListRequestResponse;
import xwsagent.wroomagent.soap.xsd.SendVehicleRequest;
import xwsagent.wroomagent.soap.xsd.SendVehicleResponse;
import xwsagent.wroomagent.soap.xsd.VehicleSoap;

@Log4j2
public class VehicleClient extends WebServiceGatewaySupport {


	public static final String MONOLITH_USER_EMAIL = "zika@maildrop.cc";

	@Autowired
	private VehicleRepository vehicleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ImageRepository imageRepository;
	
	public SendVehicleResponse send(Vehicle entity, Operation operation) {
		SendVehicleRequest request = new SendVehicleRequest();
		request.setVehicle(VehicleSoapConverter.toVehicleSoap(entity));
		request.setOperation(operation);
		
		log.info("action=send-vehicle status=started");
		SendVehicleResponse response = (SendVehicleResponse) getWebServiceTemplate().marshalSendAndReceive(request);
		log.info("action=send-vehicle status=ended");
		return response;
	}
	
	public SendImageResponse sendImage(Image entity) {
		SendImageRequest request = new SendImageRequest();
		
		ImageSoap soap = new ImageSoap();
		soap.setId(entity.getId());
		soap.setVehicleId(entity.getVehicle().getId());
		soap.setOwner(entity.getVehicle().getOwner().getEmail());
		
		try {
			soap.setBase64String(Base64.getEncoder().encode((readImage(entity.getUrlPath()))));
		} catch (IOException e) {
			System.err.println("Error while reading an image.");
			e.printStackTrace();
		}
		
		request.setImage(soap);
		
		log.info("action=send-image status=started");
		SendImageResponse response = (SendImageResponse) getWebServiceTemplate().marshalSendAndReceive(request);
		log.info("action=send-image status=ended");
		return response;
	}
	
	private byte[] readImage(String urlpath) throws IOException {
		Path path = Paths.get(urlpath);
		return Files.readAllBytes(path);
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
