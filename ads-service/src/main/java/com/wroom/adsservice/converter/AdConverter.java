package com.wroom.adsservice.converter;

import com.wroom.adsservice.domain.Ad;
import com.wroom.adsservice.domain.Comment;
import com.wroom.adsservice.domain.dto.AdDTO;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AdConverter extends AbstractConverter {

    public static AdDTO fromEntity(Ad entity) {
        return new AdDTO(
                entity.getId(),
                entity.getVehicleId(),
                entity.getPriceList().getId(),
                entity.getAvailableFrom(),
                entity.getAvailableTo(),
                entity.getMileLimit(),
                entity.isMileLimitEnabled(),
                entity.getLocation().getId(),
                entity.getAddress(),
                entity.isGps(),
                averageRate(entity.getComments())
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

    public static double averageRate(Set<Comment> comments) {
    	if(comments != null) {
    		int sum = 0;
    		List<Comment> rateList = new ArrayList<Comment>();
    		rateList.addAll(comments);
    		if(rateList.size() > 0) {
    			for(Comment c : rateList) {
    				if(c.getRate() != null) {
    					sum += c.getRate();
    				}
        		}
        		return sum*1.0/comments.size();
    		}
    	}
    	return 0;
    }
}
