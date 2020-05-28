package xwsagent.wroomagent.converter;

import xwsagent.wroomagent.domain.*;
import xwsagent.wroomagent.domain.dto.FeatureDTO;
import xwsagent.wroomagent.domain.dto.VehicleDTO;
import xwsagent.wroomagent.repository.*;

public class VehicleConverter extends AbstractConverter {


	
	public static VehicleDTO fromEntity(Vehicle entity) {
		return new VehicleDTO(
				entity.getId(),
				entity.getMileage(),
				entity.getChildSeats(),
				entity.getCdw(),
				new FeatureDTO(entity.getModelType().getId(), entity.getModelType().getName()),
				new FeatureDTO(entity.getModelType().getBrandType().getId(), entity.getModelType().getBrandType().getName()),
				new FeatureDTO(entity.getBodyType().getId(), entity.getBodyType().getName()),
				new FeatureDTO(entity.getFuelType().getId(), entity.getFuelType().getName()),
				new FeatureDTO(entity.getGearboxType().getId(), entity.getGearboxType().getName())
		);
	}
	
	public static Vehicle toEntity(VehicleDTO dto) {
		Vehicle vehicle = new Vehicle();
		vehicle.setMileage(dto.getMileage());
		vehicle.setChildSeats(dto.getChildSeats());
		vehicle.setCdw(dto.getCdw());
		vehicle.setDeleted(false);
		return vehicle;
	}
}
