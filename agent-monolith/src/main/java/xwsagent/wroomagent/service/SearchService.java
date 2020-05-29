package xwsagent.wroomagent.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import xwsagent.wroomagent.domain.Ad;
import xwsagent.wroomagent.domain.RentRequest;
import xwsagent.wroomagent.domain.dto.SearchCriteriaDTO;

@Service
public class SearchService {

	private final AdService adService;
	private final RentsService rentsService;
	
	public SearchService(AdService as, RentsService rs) {
		this.adService = as;
		this.rentsService = rs;
	}
	
	public List<Long> search(SearchCriteriaDTO criteria) {
		List<Ad> ads = this.adService.findAll();
		List<Long> ids = new ArrayList<Long>();
		
		//if there is no rent request between criteria.from and criteria.to
		if(this.adService.findLocationById(criteria.getLocationId()) != null ) {
			
//			Loop through all ads and take their rent requests
			for(Ad ad : ads) {
				List<RentRequest> rents = this.rentsService.findByAd(ad);
				System.out.println("finding rents");
			}
			
		}
		
		return ids;
	}
	
}
