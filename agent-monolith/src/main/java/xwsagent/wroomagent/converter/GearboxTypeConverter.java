package xwsagent.wroomagent.converter;

import xwsagent.wroomagent.domain.GearboxType;
import xwsagent.wroomagent.domain.dto.FeatureDTO;

public class GearboxTypeConverter extends AbstractConverter{

	public static FeatureDTO fromEntity(GearboxType entity) {
		return new FeatureDTO(
				entity.getId(),
				entity.getName()
		);
	}
	
	public static GearboxType toEntity(FeatureDTO dto) {
		GearboxType gearboxType = new GearboxType();
		gearboxType.setName(dto.getName());
		gearboxType.setDeleted(false);
		
		return gearboxType;
	}
}
