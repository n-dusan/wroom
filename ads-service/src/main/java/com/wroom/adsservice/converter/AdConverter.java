package com.wroom.adsservice.converter;

import com.wroom.adsservice.domain.Ad;
import com.wroom.adsservice.domain.Comment;
import com.wroom.adsservice.domain.dto.AdDTO;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AdConverter extends AbstractConverter {

    public static AdDTO fromEntity(Ad entity) {
    	AdDTO ret = new AdDTO(
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
                null,
                entity.getLocalId() == null ? null : entity.getLocalId(),
                entity.getOwnerUsername() == null ? null : entity.getOwnerUsername()
    	);
    	
    	try {
    		ret.setAverageRate(averageRate(entity.getComments()));
    	} catch(Exception e) {
    		System.err.println("Error during calculating average rate");
    	}
        return ret;
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
        if(dto.getLocalId() != null) {
        	ad.setLocalId(dto.getLocalId());
        }
        if(dto.getOwnerUsername() != null) {
        	ad.setOwnerUsername(dto.getOwnerUsername());
        }
        return ad;
    }

    public static double averageRate(Set<Comment> comments) {
    	if(comments != null) {
    		if(comments.size() > 0) {
    			int sum = 0;
        		int n = 0;
        		List<Comment> commentList = new ArrayList<Comment>();
        		
        		commentList.addAll(comments);
        		if(commentList.size() > 0) {
        			for(Comment c : commentList) {
        				if(c.getRate() != null && c.getRate() != 0) {
        					sum += c.getRate();
        					n++;
        				}
            		}
            		return sum*1.0/n;
        		}
    		}
    	}
    	return 0;
    }
}
