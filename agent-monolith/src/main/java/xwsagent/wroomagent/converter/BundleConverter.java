package xwsagent.wroomagent.converter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import xwsagent.wroomagent.domain.BundledRequests;
import xwsagent.wroomagent.domain.RentRequest;
import xwsagent.wroomagent.domain.dto.BundleDTO;

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
