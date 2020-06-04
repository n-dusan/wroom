package com.wroom.searchservice.converter;

import com.wroom.searchservice.domain.Vehicle;
import com.wroom.searchservice.domain.dto.FeatureDTO;
import com.wroom.searchservice.domain.dto.VehicleDTO;

public class VehicleConverter extends AbstractConverter {


	
	public static VehicleDTO fromEntity(Vehicle entity) {
		return new VehicleDTO(
				entity.getId(),
				entity.getMileage(),
				entity.getChildSeats(),
				entity.getCdw(),
				new FeatureDTO(entity.getModelType().getId(), entity.getModelType().getName(), entity.getModelType().getBrandType().getId()),
				new FeatureDTO(entity.getModelType().getBrandType().getId(), entity.getModelType().getBrandType().getName(), null),
				new FeatureDTO(entity.getBodyType().getId(), entity.getBodyType().getName(), null),
				new FeatureDTO(entity.getFuelType().getId(), entity.getFuelType().getName(), null),
				new FeatureDTO(entity.getGearboxType().getId(), entity.getGearboxType().getName(), null),
				entity.getOwner().getId()
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
