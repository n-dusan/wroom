package com.wroom.rentingservice.converter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.wroom.rentingservice.domain.BundledRequests;
import com.wroom.rentingservice.domain.RentRequest;
import com.wroom.rentingservice.domain.dto.BundleDTO;

public class BundleConverter extends AbstractConverter {

	public static BundleDTO fromEntity(BundledRequests entity) {
        return new BundleDTO(
        		entity.getId(),
        		getRequestIds(entity.getRequests())
        );
    }

//    public static BundledRequests toEntity(BundleDTO dto) {
//        BundledRequests b = new BundledRequests();
//        return b;
//    }
	
    private static Set<Long> getRequestIds(Set<RentRequest> requests) {
    	List<RentRequest> list = new ArrayList<RentRequest>(requests);
    	Set<Long> ret = new HashSet<Long>();
    	for( RentRequest r : list) {
    		ret.add(r.getId());
    	}
    	
    	return ret;
    }
}
