package com.wroom.rentingservice.service;

import org.springframework.stereotype.Service;

import com.wroom.rentingservice.domain.BundledRequests;
import com.wroom.rentingservice.repository.BundleRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BundleService {

	private final BundleRepository bundleRepository;
	
	public BundledRequests save(BundledRequests b) {
		return this.bundleRepository.save(b);
	}
}
