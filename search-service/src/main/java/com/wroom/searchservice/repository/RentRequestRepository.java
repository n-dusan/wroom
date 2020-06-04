package com.wroom.searchservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wroom.searchservice.domain.Ad;
import com.wroom.searchservice.domain.RentRequest;

public interface RentRequestRepository extends JpaRepository<RentRequest, Long> {

	List<RentRequest> findByAd(Ad ad);
}
