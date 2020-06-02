package com.wroom.vehicleservice.converter;

import com.wroom.vehicleservice.domain.BodyType;
import com.wroom.vehicleservice.domain.dto.FeatureDTO;

public class BodyTypeConverter extends AbstractConverter {

    public static FeatureDTO fromEntity(BodyType entity) {
        return new FeatureDTO(
                entity.getId(),
                entity.getName(),
                null);
    }

    public static BodyType toEntity(FeatureDTO dto) {
        BodyType bodyType = new BodyType();
        bodyType.setName(dto.getName());
        bodyType.setDeleted(false);

        return bodyType;
    }
}
