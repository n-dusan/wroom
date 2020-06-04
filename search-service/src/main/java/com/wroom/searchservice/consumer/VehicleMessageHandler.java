package com.wroom.searchservice.consumer;

import org.springframework.stereotype.Component;

import com.wroom.searchservice.consumer.message.VehicleMessage;
import com.wroom.searchservice.converter.BodyTypeConverter;
import com.wroom.searchservice.converter.BrandTypeConverter;
import com.wroom.searchservice.converter.FuelTypeConverter;
import com.wroom.searchservice.converter.GearboxTypeConverter;
import com.wroom.searchservice.converter.AMQP.FeatureMessageConverter;
import com.wroom.searchservice.converter.AMQP.VehicleMessageConverter;
import com.wroom.searchservice.service.AdService;
import com.wroom.searchservice.service.BodyTypeService;
import com.wroom.searchservice.service.BrandTypeService;
import com.wroom.searchservice.service.FuelTypeService;
import com.wroom.searchservice.service.GearboxTypeService;
import com.wroom.searchservice.service.ModelTypeService;
import com.wroom.searchservice.service.VehicleService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class VehicleMessageHandler {

	private final VehicleService vehicleService;
	private final AdService adService;
	private final ModelTypeService modelTypeService;
	private final BrandTypeService brandTypeService;
	private final FuelTypeService fuelTypeService;
	private final BodyTypeService bodyTypeService;
	private final GearboxTypeService gearboxService;

	

	public VehicleMessageHandler(VehicleService vehicleService, AdService adService, ModelTypeService modelTypeService,
			BrandTypeService brandTypeService, FuelTypeService fuelTypeService, BodyTypeService bodyTypeService,
			GearboxTypeService gearboxService) {
		this.vehicleService = vehicleService;
		this.adService = adService;
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
			System.out.println("HERE I AM");
			break;
		case MODEL_TYPE:
			this.modelTypeService.save(FeatureMessageConverter.fromMessage(message.getModelType()));
			break;
		case BRAND_TYPE:
			this.brandTypeService.save(BrandTypeConverter.toEntity(FeatureMessageConverter.fromMessage(message.getBrandType())));
			break;
		case FUEL_TYPE:
			this.fuelTypeService.save(FuelTypeConverter.toEntity(FeatureMessageConverter.fromMessage(message.getFuelType())));
			break;
		case GEARBOX_TYPE:
			this.gearboxService.save(GearboxTypeConverter.toEntity(FeatureMessageConverter.fromMessage(message.getGearboxType())));
			break;
		case BODY_TYPE:
			this.bodyTypeService.save(BodyTypeConverter.toEntity(FeatureMessageConverter.fromMessage(message.getBodyType())));
			break;
		default:
			break;
		}
	}
	
	public void updateEntity(VehicleMessage message) {
		switch (message.getEntity()) {
		case VEHICLE:
			
			break;
		case MODEL_TYPE:

			break;
		case BRAND_TYPE:

			break;
		case FUEL_TYPE:

			break;
		case GEARBOX_TYPE:

			break;
		case BODY_TYPE:

			break;
		default:
			break;
		}
	}
	
	public void deleteEntity(VehicleMessage message) {
		switch (message.getEntity()) {
		case VEHICLE:

			break;
		case MODEL_TYPE:

			break;
		case BRAND_TYPE:

			break;
		case FUEL_TYPE:

			break;
		case GEARBOX_TYPE:

			break;
		case BODY_TYPE:

			break;
		default:
			break;
		}
	}

}
