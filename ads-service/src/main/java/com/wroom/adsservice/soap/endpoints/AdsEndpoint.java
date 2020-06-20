package com.wroom.adsservice.soap.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.wroom.adsservice.converter.AdConverter;
import com.wroom.adsservice.domain.Ad;
import com.wroom.adsservice.repository.LocationRepository;
import com.wroom.adsservice.repository.PriceListRepository;
import com.wroom.adsservice.service.AdService;
import com.wroom.adsservice.soap.converters.AdSoapConverter;
import com.wroom.adsservice.soap.xsd.Operation;
import com.wroom.adsservice.soap.xsd.SendAdRequest;
import com.wroom.adsservice.soap.xsd.SendAdResponse;

@Endpoint
public class AdsEndpoint {

private static final String NAMESPACE_URI ="http://ftn.com/wroom-agent/xsd";

	@Autowired
	private LocationRepository locationRepository;
	@Autowired
	private PriceListRepository pricelistRepository;
	@Autowired
	private AdService adService;
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "SendAdRequest")
	@ResponsePayload
	public SendAdResponse sendMessageToService(@RequestPayload SendAdRequest request) {
		System.out.println(">>>>>>>>>>> Received an Ad!");
		SendAdResponse response = new SendAdResponse();
		
		Ad entity = AdSoapConverter.fromAdSoap(request.getAd());
		entity.setPriceList(this.pricelistRepository.findOneById(request.getAd().getPriceListId()));
		entity.setLocation(this.locationRepository.findOneById(request.getAd().getLocationId()));
		
		if(request.getOperation() == Operation.CREATE) {
			try {
				Ad saved = this.adService.save(AdConverter.fromEntity(entity));
				response.setAd(AdSoapConverter.toAdSoap(saved));
				System.out.println(">>>>>>>>>>> Ad saved!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(request.getOperation() == Operation.DELETE) {
			Ad deleted = this.adService.deleteByLocalId(entity.getLocalId(), entity.getOwnerUsername());
			response.setAd(AdSoapConverter.toAdSoap(deleted));
			System.out.println(">>>>>>>>>>> Ad deleted!");
		}
		else if(request.getOperation() == Operation.UPDATE) {
			System.out.println(">>>>>>>>>>> Ad updated!");
		}
		
		return response;
	}
	
}
