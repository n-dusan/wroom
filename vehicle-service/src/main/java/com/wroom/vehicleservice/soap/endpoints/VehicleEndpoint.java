package com.wroom.vehicleservice.soap.endpoints;

import com.wroom.vehicleservice.soap.xsd.*;
import lombok.extern.log4j.Log4j2;
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

import java.util.List;

@Endpoint
@Log4j2
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
		log.info("action=receive-vehicle status=started");
		
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


		log.info("action=receive-vehicle status=ended");
		return response;
	}


	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "SendVehicleListRequestResponse")
	@ResponsePayload
	public SendVehicleListRequestResponse sendVehicle(@RequestPayload SendVehicleListRequestResponse request) {
		log.info("action=sync-vehicles status=started");

		SendVehicleListRequestResponse response = new SendVehicleListRequestResponse();

		List<VehicleSoap> soapList = request.getVehicle();
		for (VehicleSoap vehicleSoap : soapList) {
			Vehicle vehicle = this.vehicleRepository.findByLocalId(vehicleSoap.getId(), request.getCompanyEmail());

			if(vehicle == null) {
				Vehicle entityToSave = VehicleSoapConverter.fromVehicleSoap(vehicleSoap);

				entityToSave.setModelType(this.modelTypeRepository.findByName(vehicleSoap.getModelType().getModelName()));
				entityToSave.setBodyType(this.bodyTypeRepository.findOneByName(vehicleSoap.getBodyType()));
				entityToSave.setFuelType(this.fuelTypeRepository.findByName(vehicleSoap.getFuelType()));
				entityToSave.setGearboxType(this.gearboxTypeRepository.findByName(vehicleSoap.getGearboxType()));

				this.vehicleRepository.save(entityToSave);
			}
		}
		log.info("action=sync-vehicles status=ended");
		return response;
	}
}
