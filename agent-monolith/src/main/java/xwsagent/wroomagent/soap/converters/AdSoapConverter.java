package xwsagent.wroomagent.soap.converters;

import xwsagent.wroomagent.converter.AbstractConverter;
import xwsagent.wroomagent.domain.Ad;
import xwsagent.wroomagent.soap.xsd.AdSoap;

public class AdSoapConverter extends AbstractConverter {

	public static AdSoap toAdSoap(Ad entity) {
		AdSoap ret = new AdSoap();
		ret.setId(entity.getId());
		ret.setPublishDate(entity.getPublishDate());
		ret.setAvailableFrom(entity.getAvailableFrom());
		ret.setAvailableTo(entity.getAvailableTo());
		
		if(entity.getMileLimit() != null) {
			ret.setMileLimit(entity.getMileLimit());
		}
		
		ret.setGps(entity.isGps());
		ret.setDeleted(entity.isDeleted());
		ret.setAddress(entity.getAddress());
		ret.setVehicleId(entity.getVehicle().getId());
		ret.setPriceListId(entity.getPriceList().getId());
		ret.setLocationId(entity.getLocation().getId());
		ret.setOwnerUsername(entity.getVehicle().getOwner().getEmail());
		return ret;
	}
	
}
