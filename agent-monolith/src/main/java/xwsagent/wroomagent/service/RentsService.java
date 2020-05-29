package xwsagent.wroomagent.service;

import java.util.List;

import org.springframework.stereotype.Service;

import xwsagent.wroomagent.domain.Ad;
import xwsagent.wroomagent.domain.RentRequest;
import xwsagent.wroomagent.repository.RentRequestRepository;

@Service
public class RentsService {

	private final RentRequestRepository rentRepository;
	
	public RentsService(RentRequestRepository rr) {
		this.rentRepository = rr;
	}
	
	public List<RentRequest> findAll() {
		return this.rentRepository.findAll();
	}
	
	public List<RentRequest> findByAd(Ad ad) {
		return this.rentRepository.findByAd(ad);
	}
	
}
