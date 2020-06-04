package com.wroom.vehicleservice.converter;

import com.wroom.vehicleservice.domain.dto.VehicleDTO;
import com.wroom.vehicleservice.producer.message.EntityEnum;
import com.wroom.vehicleservice.producer.message.OperationEnum;
import com.wroom.vehicleservice.producer.message.VehicleMessage;

public class AMQPVehicleConverter {

//	public static VehicleDTO fromVehicleMessage (VehicleMessage message) {
//        if(message.getEntity() == EntityEnum.BODY_TYPE) {
//            return new FeatureDTO(
//                    message.getBodyType().getId(),
//                    message.getBodyType().getName(),
//                    message.getBodyType().getBrandId());
//        }
//
//        if(message.getEntity() == EntityEnum.BRAND_TYPE) {
//            return new FeatureDTO(
//                    message.getBrandType().getId(),
//                    message.getBrandType().getName(),
//                    message.getBrandType().getBrandId());
//        }
//
//        if(message.getEntity() == EntityEnum.FUEL_TYPE) {
//            return new FeatureDTO(
//                    message.getFuelType().getId(),
//                    message.getFuelType().getName(),
//                    message.getFuelType().getBrandId());
//        }
//
//        if(message.getEntity() == EntityEnum.MODEL_TYPE) {
//            return new FeatureDTO(
//                    message.getModelType().getId(),
//                    message.getModelType().getName(),
//                    message.getModelType().getBrandId());
//        }
//
//        return null;
//    }

    public static VehicleMessage toVehicleMessage(VehicleDTO dto, OperationEnum operation) {
        VehicleMessage message = new VehicleMessage();
        message.setId(dto.getId());
        message.setMileage(dto.getMileage());
        message.setChildSeats(dto.getChildSeats());
        message.setCdw(dto.getCdw());
        message.setOwnerId(dto.getOwnerId());
        
        message.setBodyType(AMQPFeatureConverter.toFeatureMessage(dto.getBodyType(), operation, EntityEnum.BODY_TYPE));
        message.setModelType(AMQPFeatureConverter.toFeatureMessage(dto.getModelType(), operation, EntityEnum.MODEL_TYPE));
        message.setBrandType(AMQPFeatureConverter.toFeatureMessage(dto.getBrandType(), operation, EntityEnum.BRAND_TYPE));
        message.setGearboxType(AMQPFeatureConverter.toFeatureMessage(dto.getGearboxType(), operation, EntityEnum.GEARBOX_TYPE));
        message.setFuelType(AMQPFeatureConverter.toFeatureMessage(dto.getFuelType(), operation, EntityEnum.FUEL_TYPE));
        
        message.setEntity(EntityEnum.VEHICLE);
        message.setOperation(operation);

        return message;
    }
	
}
