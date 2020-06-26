package xwsagent.wroomagent.soap.converters;

import xwsagent.wroomagent.domain.Vehicle;
import xwsagent.wroomagent.soap.xsd.ModelType;
import xwsagent.wroomagent.soap.xsd.VehicleSoap;

public class VehicleSoapConverter {

	public static VehicleSoap toVehicleSoap(Vehicle entity) {
		VehicleSoap ret = new VehicleSoap();
		ret.setId(entity.getId());
		ret.setMileage(entity.getMileage());
		ret.setChildSeats(entity.getChildSeats());
		ret.setCdw(entity.getCdw());
		ret.setDeleted(entity.isDeleted());
		ret.setOwner(entity.getOwner().getEmail());
		ModelType mt = new ModelType();
		mt.setBrandName(entity.getModelType().getBrandType().getName());
		mt.setModelName(entity.getModelType().getName());
		ret.setModelType(mt);
		ret.setFuelType(entity.getFuelType().getName());
		ret.setBodyType(entity.getBodyType().getName());
		ret.setGearboxType(entity.getGearboxType().getName());
		ret.setOwnerId(entity.getOwner().getId());
		//TODO: Images
		
		return ret;
	}
	
}
