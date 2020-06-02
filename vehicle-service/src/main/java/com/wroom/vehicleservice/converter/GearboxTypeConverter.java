package com.wroom.vehicleservice.converter;

import com.wroom.vehicleservice.domain.GearboxType;
import com.wroom.vehicleservice.domain.dto.FeatureDTO;

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
