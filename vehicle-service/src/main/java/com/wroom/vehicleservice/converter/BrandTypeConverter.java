package com.wroom.vehicleservice.converter;

import com.wroom.vehicleservice.domain.BrandType;
import com.wroom.vehicleservice.domain.dto.FeatureDTO;

public class BrandTypeConverter extends AbstractConverter {

    public static FeatureDTO fromEntity(BrandType entity) {
        return new FeatureDTO(
                entity.getId(),
                entity.getName(),
                null);
    }

    public static BrandType toEntity(FeatureDTO dto) {
        BrandType brandType = new BrandType();
        brandType.setName(dto.getName());
        brandType.setDeleted(false);

        return brandType;
    }
}