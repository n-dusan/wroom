package com.wroom.rentingservice.converter;

import com.wroom.rentingservice.domain.Ad;
import com.wroom.rentingservice.domain.dto.AdDTO;


public class AdConverter extends AbstractConverter {

    public static AdDTO fromEntity(Ad entity) {
        return new AdDTO(
                entity.getId(),
                entity.getVehicleId(),
                entity.getPriceListId(),
                entity.getAvailableFrom(),
                entity.getAvailableTo(),
                entity.getMileLimit(),
                entity.isMileLimitEnabled(),
                entity.getLocationId(),
                entity.getAddress(),
                entity.isGps()
//                averageRate(entity.getRates())
        );
    }

    public static Ad toEntity(AdDTO dto) {
        Ad ad = new Ad();
        ad.setAvailableFrom(dto.getAvailableFrom());
        ad.setAvailableTo(dto.getAvailableTo());
        if(dto.isMileLimitEnabled()) {
            ad.setMileLimit(dto.getMileLimit());
            ad.setMileLimitEnabled(true);
        } else {
            ad.setMileLimitEnabled(false);
        }
        ad.setAddress(dto.getAddress());
        ad.setGps(dto.isGps());
        return ad;
    }
    
//    public static double averageRate(Set<Rate> rates) {
//    	if(rates != null) {
//    		int sum = 0;
//    		List<Rate> rateList = new ArrayList<Rate>();
//    		rateList.addAll(rates);
//    		if(rateList.size() > 0) {
//    			for(Rate r : rateList) {
//        			sum += r.getRating();
//        		}
//        		return sum*1.0/rates.size();
//    		}
//    	}
//    	return 0;
//    }
}
