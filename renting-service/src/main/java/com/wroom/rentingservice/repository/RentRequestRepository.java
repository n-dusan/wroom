package com.wroom.rentingservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wroom.rentingservice.domain.Ad;
import com.wroom.rentingservice.domain.RentRequest;

public interface RentRequestRepository extends JpaRepository<RentRequest, Long> {

	List<RentRequest> findByAd(Ad ad);
	
	List<RentRequest> findByRequestedUserId(Long requestedUserId);
}
