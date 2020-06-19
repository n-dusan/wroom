package com.wroom.vehicleservice.soap.converters;

import com.wroom.vehicleservice.domain.Vehicle;
import com.wroom.vehicleservice.soap.xsd.ModelType;
import com.wroom.vehicleservice.soap.xsd.VehicleSoap;

public class VehicleSoapConverter {

	public static VehicleSoap toVehicleSoap(Vehicle entity) {
		VehicleSoap ret = new VehicleSoap();
		ret.setId(entity.getId());
		ret.setMileage(entity.getMileage());
		ret.setChildSeats(entity.getChildSeats());
		ret.setCdw(entity.getCdw());
		ret.setDeleted(entity.isDeleted());
//		ret.setOwner(entity.getOwnerUsername());
		ret.setBodyType(entity.getBodyType().toString());
		ModelType mt = new ModelType();
		mt.setModelName(entity.getModelType().getName());
		mt.setBrandName(entity.getModelType().getName());
		ret.setModelType(mt);
		ret.setFuelType(entity.getFuelType().getName());
		ret.setGearboxType(entity.getGearboxType().getName());
		
//		images
		
		return ret;
	}
	
	public static Vehicle fromVehicleSoap(VehicleSoap soap) {
		Vehicle ret = new Vehicle();
//		ret.setLocalId(soap.getId());
		ret.setMileage(soap.getMileage());
		ret.setChildSeats(soap.getChildSeats());
		ret.setCdw(soap.isCdw());
		ret.setDeleted(soap.isDeleted());
//		ret.setOwnerUsername(soap.getOwner());
		
		//Vehicle features are set in calling method
		
		return ret;
	}
	
}
