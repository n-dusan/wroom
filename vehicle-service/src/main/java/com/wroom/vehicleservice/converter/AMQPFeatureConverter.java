package com.wroom.vehicleservice.converter;

import com.wroom.vehicleservice.domain.BodyType;
import com.wroom.vehicleservice.domain.dto.FeatureDTO;
import com.wroom.vehicleservice.producer.message.EntityEnum;
import com.wroom.vehicleservice.producer.message.FeatureMessage;
import com.wroom.vehicleservice.producer.message.OperationEnum;
import com.wroom.vehicleservice.producer.message.VehicleMessage;

public class AMQPFeatureConverter {

    public static FeatureDTO fromFeatureMessage (VehicleMessage message) {
        if(message.getEntity() == EntityEnum.BODY_TYPE) {
            return new FeatureDTO(
                    message.getBodyType().getId(),
                    message.getBodyType().getName(),
                    message.getBodyType().getBrandId());
        }

        if(message.getEntity() == EntityEnum.BRAND_TYPE) {
            return new FeatureDTO(
                    message.getBrandType().getId(),
                    message.getBrandType().getName(),
                    message.getBrandType().getBrandId());
        }

        if(message.getEntity() == EntityEnum.FUEL_TYPE) {
            return new FeatureDTO(
                    message.getFuelType().getId(),
                    message.getFuelType().getName(),
                    message.getFuelType().getBrandId());
        }

        if(message.getEntity() == EntityEnum.MODEL_TYPE) {
            return new FeatureDTO(
                    message.getModelType().getId(),
                    message.getModelType().getName(),
                    message.getModelType().getBrandId());
        }

        return null;
    }

    public static VehicleMessage toFeatureMessage(FeatureDTO dto, OperationEnum operation, EntityEnum entity) {
        VehicleMessage message = new VehicleMessage();

        if(entity == EntityEnum.BODY_TYPE) {
            message.setBodyType(new FeatureMessage(
                    dto.getId(),
                    dto.getName(),
                    dto.getBrandId()
            ));
            message.setEntity(EntityEnum.BODY_TYPE);
        }

        if(entity == EntityEnum.BRAND_TYPE) {
            message.setBrandType(new FeatureMessage(
                    dto.getId(),
                    dto.getName(),
                    dto.getBrandId()
            ));
            message.setEntity(EntityEnum.BRAND_TYPE);
        }

        if(entity == EntityEnum.MODEL_TYPE) {
            message.setModelType(new FeatureMessage(
                    dto.getId(),
                    dto.getName(),
                    dto.getBrandId()
            ));
            message.setEntity(EntityEnum.MODEL_TYPE);
        }

        if(entity == EntityEnum.GEARBOX_TYPE) {
            message.setGearboxType(new FeatureMessage(
                    dto.getId(),
                    dto.getName(),
                    dto.getBrandId()
            ));
            message.setEntity(EntityEnum.GEARBOX_TYPE);
        }

        if(entity == EntityEnum.FUEL_TYPE) {
            message.setFuelType(new FeatureMessage(
                    dto.getId(),
                    dto.getName(),
                    dto.getBrandId()
            ));
            message.setEntity(EntityEnum.FUEL_TYPE);
        }

        message.setOperation(operation);

        return message;
    }


}
