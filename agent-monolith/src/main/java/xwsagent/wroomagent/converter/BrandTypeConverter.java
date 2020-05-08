package xwsagent.wroomagent.converter;

import xwsagent.wroomagent.domain.BrandType;
import xwsagent.wroomagent.domain.dto.FeatureDTO;

public class BrandTypeConverter extends AbstractConverter {

	public static FeatureDTO fromEntity(BrandType entity) {
		return new FeatureDTO(
				entity.getId(),
				entity.getName());
	}
	
	public static BrandType toEntity(FeatureDTO dto) {
		BrandType brandType = new BrandType();
		brandType.setName(dto.getName());
		brandType.setDeleted(false);
		
		return brandType;
	}
}
