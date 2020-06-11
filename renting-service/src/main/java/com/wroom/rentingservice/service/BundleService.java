package com.wroom.rentingservice.service;

import com.wroom.rentingservice.domain.RentRequest;
import com.wroom.rentingservice.exception.GeneralException;
import org.springframework.stereotype.Service;

import com.wroom.rentingservice.domain.BundledRequests;
import com.wroom.rentingservice.repository.BundleRepository;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BundleService {

	private final BundleRepository bundleRepository;
	
	public BundledRequests save(BundledRequests b) {
		return this.bundleRepository.save(b);
	}

	public BundledRequests findById(Long id) {
	 	return bundleRepository.findById(id).orElseThrow(
				() -> new GeneralException("Unable to find reference to " + id.toString() + " bundle"));
	}


	public List<RentRequest> findBundledRentRequests(Long bundleId) {
		BundledRequests bundledRequests = findById(bundleId);

		List<RentRequest> requestList  = new ArrayList<>(bundledRequests.getRequests());

		return requestList;
	}
}
