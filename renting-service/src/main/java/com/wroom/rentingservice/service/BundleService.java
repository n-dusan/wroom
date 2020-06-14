package com.wroom.rentingservice.service;

import com.wroom.rentingservice.domain.RentRequest;
import com.wroom.rentingservice.exception.GeneralException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wroom.rentingservice.domain.BundledRequests;
import com.wroom.rentingservice.repository.BundleRepository;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Service
public class BundleService {

	//neophodan autowired ovde zbog circular bean dependencies
	@Autowired
	private BundleRepository bundleRepository;

	@Autowired
	private RentsService rentsService;

	
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


	public List<RentRequest> decline(Long bundleId) {
		List<RentRequest> requestList = findBundledRentRequests(bundleId);

		for (RentRequest rentRequest : requestList) {
			this.rentsService.decline(rentRequest.getId());
		}

		return requestList;
	}


	public List<RentRequest> accept(Long bundleId) {
		List<RentRequest> requestList = findBundledRentRequests(bundleId);

		for (RentRequest rentRequest : requestList) {
			this.rentsService.accept(rentRequest.getId());
		}

		return requestList;
	}
}
