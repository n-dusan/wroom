package com.wroom.searchservice.service;

import org.springframework.stereotype.Service;

import com.wroom.searchservice.domain.BundledRequests;
import com.wroom.searchservice.repository.BundleRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BundleService {

	private final BundleRepository bundleRepository;
	
	public BundledRequests save(BundledRequests b) {
		return this.bundleRepository.save(b);
	}
}
