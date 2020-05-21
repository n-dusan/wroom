package xwsagent.wroomagent.converter;

import xwsagent.wroomagent.domain.Vehicle;
import xwsagent.wroomagent.domain.dto.VehicleDTO;

public class VehicleConverter extends AbstractConverter {
	
	public static VehicleDTO fromEntity(Vehicle entity) {
		return new VehicleDTO(
				entity.getId(),
				entity.getMileage(),
				entity.getChildSeats(),
				entity.getCdw(),
				entity.getModelType(),
				entity.getBrandType(),
				entity.getBodyType(),
				entity.getFuelType(),
				entity.getGearboxType()
		);
	}
	
	public static Vehicle toEntity(VehicleDTO dto) {
		Vehicle vehicle = new Vehicle();
		vehicle.setMileage(dto.getMileage());
		vehicle.setChildSeats(dto.getChildSeats());
		vehicle.setCdw(dto.getCdw());
		vehicle.setModelType(dto.getModelType());
		vehicle.setBrandType(dto.getBrandType());
		vehicle.setBodyType(dto.getBodyType());
		vehicle.setFuelType(dto.getFuelType());
		vehicle.setGearboxType(dto.getGearboxType());
		return vehicle;
	}
}
