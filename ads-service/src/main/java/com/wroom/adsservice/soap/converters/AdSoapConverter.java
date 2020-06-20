package com.wroom.adsservice.soap.converters;

import com.wroom.adsservice.domain.Ad;
import com.wroom.adsservice.soap.xsd.AdSoap;

public class AdSoapConverter {

	public static Ad fromAdSoap(AdSoap soap) {
		Ad ret = new Ad();
		ret.setLocalId(soap.getId());
		
		if(soap.getOwnerUsername() != null) {
			ret.setOwnerUsername(soap.getOwnerUsername());
		}
		
		ret.setPublishDate(soap.getPublishDate());
		ret.setAvailableFrom(soap.getAvailableFrom());
		ret.setAvailableTo(soap.getAvailableTo());
		
		if(soap.getMileLimit() != 0) {
			ret.setMileLimit(soap.getMileLimit());
			ret.setMileLimitEnabled(true);
		} else {
			ret.setMileLimit(0d);
			ret.setMileLimitEnabled(false);
		}
		
		ret.setGps(soap.isGps());
		ret.setDeleted(soap.isDeleted());
		ret.setAddress(soap.getAddress());
		ret.setVehicleId(soap.getVehicleId());
//		ret.setPriceList(soap.getPriceListId());
//		ret.setLocation(soap.getLocationId());
		
		return ret;
	}
	
	public static AdSoap toAdSoap(Ad entity) {
		AdSoap ret = new AdSoap();
		if(entity.getLocalId() != null) {
			ret.setId(entity.getLocalId());
		}
		ret.setPublishDate(entity.getPublishDate());
		ret.setAvailableFrom(entity.getAvailableFrom());
		ret.setAvailableTo(entity.getAvailableTo());
		
		if(entity.getMileLimit() != null) {
			ret.setMileLimit(entity.getMileLimit());
		}
		
		ret.setGps(entity.isGps());
		ret.setDeleted(entity.isDeleted());
		ret.setAddress(entity.getAddress());
		ret.setVehicleId(entity.getVehicleId());
		ret.setPriceListId(entity.getPriceList().getId());
		ret.setLocationId(entity.getLocation().getId());
		return ret;
	}
	
}
