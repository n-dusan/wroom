package com.wroom.adsservice.soap.endpoints;

import com.wroom.adsservice.repository.AdRepository;
import com.wroom.adsservice.soap.xsd.*;
import lombok.extern.log4j.Log4j2;
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

import java.util.List;

@Endpoint
@Log4j2
public class AdsEndpoint {

	private static final String NAMESPACE_URI ="http://ftn.com/wroom-agent/xsd";
	private static final String MONOLITH_EMAIL = "zika@maildrop.cc";

	@Autowired
	private LocationRepository locationRepository;
	@Autowired
	private PriceListRepository pricelistRepository;
	@Autowired
	private AdService adService;
	@Autowired
	private AdRepository adRepository;
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "SendAdRequest")
	@ResponsePayload
	public SendAdResponse sendAd(@RequestPayload SendAdRequest request) {
		log.info("action=receive-ad status=started");
		SendAdResponse response = new SendAdResponse();
		
		Ad entity = AdSoapConverter.fromAdSoap(request.getAd());
		entity.setPriceList(this.pricelistRepository.findOneById(request.getAd().getPriceListId()));
		entity.setLocation(this.locationRepository.findOneById(request.getAd().getLocationId()));
		
		if(request.getOperation() == Operation.CREATE) {
			try {
				Ad saved = this.adService.save(AdConverter.fromEntity(entity));
				response.setAd(AdSoapConverter.toAdSoap(saved));
				log.info(">Ad saved");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(request.getOperation() == Operation.DELETE) {
			Ad deleted = this.adService.deleteByLocalId(entity.getLocalId(), entity.getOwnerUsername());
			response.setAd(AdSoapConverter.toAdSoap(deleted));
			log.info(">Ad deleted");
		}
		else if(request.getOperation() == Operation.UPDATE) {
			Ad updated = this.adService.update(entity);
			response.setAd(AdSoapConverter.toAdSoap(updated));
			log.info(">Ad updated");
		}

		log.info("action=receive-ad status=ended");
		return response;
	}


	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "SendAdListRequestResponse")
	@ResponsePayload
	public SendAdListRequestResponse sync(@RequestPayload SendAdListRequestResponse request) {
		log.info("action=sync-ads status=started");

		if(!request.getCompanyEmail().equals(MONOLITH_EMAIL)) {
			return null;
		}

		SendAdListRequestResponse response = new SendAdListRequestResponse();

		List<AdSoap> soapList = request.getAd();
		for (AdSoap adSoap : soapList) {
			Ad ad = this.adRepository.findByLocalId(adSoap.getId(), request.getCompanyEmail());

			if(ad == null) {
				Ad entityToSave = AdSoapConverter.fromAdSoap(adSoap);

				entityToSave.setPriceList(this.pricelistRepository.findOneById(adSoap.getPriceListId()));
				entityToSave.setLocation(this.locationRepository.findOneById(adSoap.getLocationId()));

				this.adRepository.save(entityToSave);
			}
		}
		log.info("action=sync-ads status=ended");
		return response;
	}
	
}
