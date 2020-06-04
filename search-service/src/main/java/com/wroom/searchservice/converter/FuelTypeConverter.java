package com.wroom.searchservice.converter;

import com.wroom.searchservice.domain.FuelType;
import com.wroom.searchservice.domain.dto.FeatureDTO;

public class FuelTypeConverter extends AbstractConverter {

	public static FeatureDTO fromEntity(FuelType entity) {
		return new FeatureDTO(
				entity.getId(),
				entity.getName(),
				null);
	}
	
	public static FuelType toEntity(FeatureDTO dto) {
		FuelType fuelType = new FuelType();
		fuelType.setName(dto.getName());
		fuelType.setDeleted(false);
		
		return fuelType;
	}
}
