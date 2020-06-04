package com.wroom.searchservice.consumer.message.handlers;

import org.springframework.stereotype.Component;

import com.wroom.searchservice.consumer.message.AdsMessage;
import com.wroom.searchservice.converter.LocationConverter;
import com.wroom.searchservice.converter.PriceListConverter;
import com.wroom.searchservice.converter.AMQP.LocationMessageConverter;
import com.wroom.searchservice.converter.AMQP.PriceListMessageConverter;
import com.wroom.searchservice.service.AdService;
import com.wroom.searchservice.service.PriceListService;

@Component
public class AdsMessageHandler {

	private final AdService adService;
	private final PriceListService pricelistService;
	
	public AdsMessageHandler(AdService adService,
			PriceListService pricelistService) {
		this.adService = adService;
		this.pricelistService = pricelistService;
	}

	public void createEntity(AdsMessage message) {
		switch (message.getEntity()) {
		case AD:
			// TODO
			break;
		case LOCATION:
			this.adService.saveLocation(LocationConverter.toEntity(LocationMessageConverter.fromMessage(message.getLocation())));
			System.out.println(">>> Successfully created location.");
			break;
		case PRICELIST:
			this.pricelistService.save(PriceListConverter.toEntity(PriceListMessageConverter.fromMessage(message.getPriceList())));
			System.out.println(">>> Successfully created pricelist.");
			break;
		default:
			break;
		}
	}

	public void updateEntity(AdsMessage message) {
		switch (message.getEntity()) {
		case AD:
			// TODO
			break;
		case LOCATION:
//			this.adService.updateLocation(LocationConverter.toEntity(LocationMessageConverter.fromMessage(message.getLocation())));
//			System.out.println(">>> Successfully updated location.");
			break;
		case PRICELIST:
			this.pricelistService.save(PriceListConverter.toEntity(PriceListMessageConverter.fromMessage(message.getPriceList())));
			System.out.println(">>> Successfully updated pricelist.");
			break;
		default:
			break;
		}
	}

	public void deleteEntity(AdsMessage message) {
		switch (message.getEntity()) {
		case AD:
			// TODO
			break;
		case LOCATION:
//			this.adService.deleteLocation(LocationConverter.toEntity(LocationMessageConverter.fromMessage(message.getLocation())));
//			System.out.println(">>> Successfully deleted location.");
			break;
		case PRICELIST:
			this.pricelistService.delete(message.getPriceList().getId());
			System.out.println(">>> Successfully deleted pricelist.");
			break;
		default:
			break;
		}
	}
}
