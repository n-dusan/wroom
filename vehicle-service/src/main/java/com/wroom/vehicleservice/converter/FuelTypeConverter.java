package com.wroom.vehicleservice.converter;

import com.wroom.vehicleservice.domain.FuelType;
import com.wroom.vehicleservice.domain.dto.FeatureDTO;

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

