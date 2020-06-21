package com.wroom.vehicleservice.soap.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.wroom.vehicleservice.domain.Vehicle;
import com.wroom.vehicleservice.repository.BodyTypeRepository;
import com.wroom.vehicleservice.repository.FuelTypeRepository;
import com.wroom.vehicleservice.repository.GearboxTypeRepository;
import com.wroom.vehicleservice.repository.ModelTypeRepository;
import com.wroom.vehicleservice.repository.VehicleRepository;
import com.wroom.vehicleservice.service.VehicleService;
import com.wroom.vehicleservice.soap.converters.VehicleSoapConverter;
import com.wroom.vehicleservice.soap.xsd.Operation;
import com.wroom.vehicleservice.soap.xsd.SendVehicleRequest;
import com.wroom.vehicleservice.soap.xsd.SendVehicleResponse;

@Endpoint
public class VehicleEndpoint {

	private static final String NAMESPACE_URI ="http://ftn.com/wroom-agent/xsd";
	
	@Autowired
	private VehicleRepository vehicleRepository;
	@Autowired
	private VehicleService vehicleService;
	@Autowired
	private ModelTypeRepository modelTypeRepository;
	@Autowired
	private BodyTypeRepository bodyTypeRepository;
	@Autowired
	private FuelTypeRepository fuelTypeRepository;
	@Autowired
	private GearboxTypeRepository gearboxTypeRepository;
	

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "SendVehicleRequest")
	@ResponsePayload
	public SendVehicleResponse sendVehicle(@RequestPayload SendVehicleRequest request) {
		System.out.println(">>>>>>>>>>> Received a Vehicle!");
		
		SendVehicleResponse response = new SendVehicleResponse();
		
		Vehicle entity = VehicleSoapConverter.fromVehicleSoap(request.getVehicle());
		entity.setModelType(this.modelTypeRepository.findByName(request.getVehicle().getModelType().getModelName()));
		entity.setBodyType(this.bodyTypeRepository.findOneByName(request.getVehicle().getBodyType()));
		entity.setFuelType(this.fuelTypeRepository.findByName(request.getVehicle().getFuelType()));
		entity.setGearboxType(this.gearboxTypeRepository.findByName(request.getVehicle().getGearboxType()));
		 
		if(request.getOperation() == Operation.CREATE) {
			Vehicle saved = this.vehicleService.save(entity);
			response.setVehicle(VehicleSoapConverter.toVehicleSoap(saved));
		}
		else if(request.getOperation() == Operation.DELETE) {
			Vehicle deleted = this.vehicleService.deleteByLocalId(entity.getLocalId(), entity.getOwnerUsername());
			response.setVehicle(VehicleSoapConverter.toVehicleSoap(deleted));
		}
		else if(request.getOperation() == Operation.UPDATE) {
			Vehicle updated = this.vehicleService.update(entity);
			response.setVehicle(VehicleSoapConverter.toVehicleSoap(updated));
		}
		
		
		System.out.println(">>>>>>>>>>> Success!");
		return response;
	}
}
