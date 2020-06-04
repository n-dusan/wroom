package com.wroom.searchservice.consumer.message.handlers;

import org.springframework.stereotype.Component;

import com.wroom.searchservice.consumer.message.VehicleMessage;
import com.wroom.searchservice.converter.BodyTypeConverter;
import com.wroom.searchservice.converter.BrandTypeConverter;
import com.wroom.searchservice.converter.FuelTypeConverter;
import com.wroom.searchservice.converter.GearboxTypeConverter;
import com.wroom.searchservice.converter.AMQP.FeatureMessageConverter;
import com.wroom.searchservice.converter.AMQP.VehicleMessageConverter;
import com.wroom.searchservice.service.BodyTypeService;
import com.wroom.searchservice.service.BrandTypeService;
import com.wroom.searchservice.service.FuelTypeService;
import com.wroom.searchservice.service.GearboxTypeService;
import com.wroom.searchservice.service.ModelTypeService;
import com.wroom.searchservice.service.VehicleService;

@Component
public class VehicleMessageHandler {

	private final VehicleService vehicleService;
	private final ModelTypeService modelTypeService;
	private final BrandTypeService brandTypeService;
	private final FuelTypeService fuelTypeService;
	private final BodyTypeService bodyTypeService;
	private final GearboxTypeService gearboxService;

	
	public VehicleMessageHandler(VehicleService vehicleService, ModelTypeService modelTypeService,
			BrandTypeService brandTypeService, FuelTypeService fuelTypeService, BodyTypeService bodyTypeService,
			GearboxTypeService gearboxService) {
		this.vehicleService = vehicleService;
		this.modelTypeService = modelTypeService;
		this.brandTypeService = brandTypeService;
		this.fuelTypeService = fuelTypeService;
		this.bodyTypeService = bodyTypeService;
		this.gearboxService = gearboxService;
	}

	public void createEntity(VehicleMessage message) {
		switch (message.getEntity()) {
		case VEHICLE:
			this.vehicleService.save(VehicleMessageConverter.fromMessage(message));
			System.out.println(">>> Successfully created vehicle.");
			break;
		case MODEL_TYPE:
			this.modelTypeService.save(FeatureMessageConverter.fromMessage(message.getModelType()));
			System.out.println(">>> Successfully created model type.");
			break;
		case BRAND_TYPE:
			this.brandTypeService.save(BrandTypeConverter.toEntity(FeatureMessageConverter.fromMessage(message.getBrandType())));
			System.out.println(">>> Successfully created brand type.");
			break;
		case FUEL_TYPE:
			this.fuelTypeService.save(FuelTypeConverter.toEntity(FeatureMessageConverter.fromMessage(message.getFuelType())));
			System.out.println(">>> Successfully created fuel type.");
			break;
		case GEARBOX_TYPE:
			this.gearboxService.save(GearboxTypeConverter.toEntity(FeatureMessageConverter.fromMessage(message.getGearboxType())));
			System.out.println(">>> Successfully created gearbox type.");
			break;
		case BODY_TYPE:
			this.bodyTypeService.save(BodyTypeConverter.toEntity(FeatureMessageConverter.fromMessage(message.getBodyType())));
			System.out.println(">>> Successfully created body type.");
			break;
		default:
			break;
		}
	}
	
	public void updateEntity(VehicleMessage message) {
		switch (message.getEntity()) {
		case VEHICLE:
			this.vehicleService.update(VehicleMessageConverter.fromMessage(message));
			System.out.println(">>> Successfully updated vehicle.");
			break;
		case MODEL_TYPE:
			this.modelTypeService.update(FeatureMessageConverter.fromMessage(message.getModelType()));
			System.out.println(">>> Successfully updated model type.");
			break;
		case BRAND_TYPE:
			this.brandTypeService.update(FeatureMessageConverter.fromMessage(message.getBrandType()));
			System.out.println(">>> Successfully updated brand type.");
			break;
		case FUEL_TYPE:
			this.fuelTypeService.update(FeatureMessageConverter.fromMessage(message.getFuelType()));
			System.out.println(">>> Successfully updated fuel type.");
			break;
		case GEARBOX_TYPE:
			this.gearboxService.update(FeatureMessageConverter.fromMessage(message.getGearboxType()));
			System.out.println(">>> Successfully updated gearbox type.");
			break;
		case BODY_TYPE:
			this.bodyTypeService.update(FeatureMessageConverter.fromMessage(message.getBodyType()));
			System.out.println(">>> Successfully updated body type.");
			break;
		default:
			break;
		}
	}
	
	public void deleteEntity(VehicleMessage message) {
		switch (message.getEntity()) {
		case VEHICLE:
			this.vehicleService.delete(message.getId());
			System.out.println(">>> Successfully deleted vehicle.");
			break;
		case MODEL_TYPE:
			this.modelTypeService.delete(message.getModelType().getId());
			System.out.println(">>> Successfully deleted model type.");
			break;
		case BRAND_TYPE:
			this.brandTypeService.delete(message.getBrandType().getId());
			System.out.println(">>> Successfully deleted brand type.");
			break;
		case FUEL_TYPE:
			this.fuelTypeService.delete(message.getFuelType().getId());
			System.out.println(">>> Successfully deleted fuel type.");
			break;
		case GEARBOX_TYPE:
			this.gearboxService.delete(message.getGearboxType().getId());
			System.out.println(">>> Successfully deleted gearbox type.");
			break;
		case BODY_TYPE:
			this.bodyTypeService.delete(message.getBodyType().getId());
			System.out.println(">>> Successfully deleted body type.");
			break;
		default:
			break;
		}
	}

}
