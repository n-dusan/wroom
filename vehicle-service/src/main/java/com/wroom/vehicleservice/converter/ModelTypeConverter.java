package com.wroom.vehicleservice.converter;

import com.wroom.vehicleservice.domain.ModelType;
import com.wroom.vehicleservice.domain.dto.FeatureDTO;

public class ModelTypeConverter extends AbstractConverter {

    public static FeatureDTO fromEntity(ModelType entity) {
        return new FeatureDTO(
                entity.getId(),
                entity.getName(),
                (entity.getBrandType() != null) ? entity.getBrandType().getId() : null);
    }

    public static ModelType toEntity(FeatureDTO dto) {
        ModelType modelType = new ModelType();
        modelType.setName(dto.getName());
        modelType.setDeleted(false);

        return modelType;
    }
}