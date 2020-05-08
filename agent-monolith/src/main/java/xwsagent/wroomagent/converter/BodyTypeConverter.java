package xwsagent.wroomagent.converter;

import xwsagent.wroomagent.domain.BodyType;
import xwsagent.wroomagent.domain.dto.FeatureDTO;

public class BodyTypeConverter extends AbstractConverter {
	
	public static FeatureDTO fromEntity(BodyType entity) {
		return new FeatureDTO(
				entity.getId(),
				entity.getName());
	}
	
	public static BodyType toEntity(FeatureDTO dto) {
		BodyType bodyType = new BodyType();
		bodyType.setName(dto.getName());
		bodyType.setDeleted(false);
		
		return bodyType;
	}
}
