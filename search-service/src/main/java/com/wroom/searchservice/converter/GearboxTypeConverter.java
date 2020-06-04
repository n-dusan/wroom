package com.wroom.searchservice.converter;

import com.wroom.searchservice.domain.GearboxType;
import com.wroom.searchservice.domain.dto.FeatureDTO;

public class GearboxTypeConverter extends AbstractConverter{

	public static FeatureDTO fromEntity(GearboxType entity) {
		return new FeatureDTO(
				entity.getId(),
				entity.getName(),
				null
		);
	}
	
	public static GearboxType toEntity(FeatureDTO dto) {
		GearboxType gearboxType = new GearboxType();
		gearboxType.setName(dto.getName());
		gearboxType.setDeleted(false);
		
		return gearboxType;
	}
}
