package xwsagent.wroomagent.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import xwsagent.wroomagent.domain.Ad;
import xwsagent.wroomagent.domain.Comment;
import xwsagent.wroomagent.domain.dto.AdDTO;


public class AdConverter extends AbstractConverter {

    public static AdDTO fromEntity(Ad entity) {
        return new AdDTO(
                entity.getId(),
                entity.getVehicle().getId(),
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
        			sum += c.getRating();
        		}
        		return sum*1.0/comments.size();
    		}
    	}
    	return 0;
    }
}
