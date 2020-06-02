package xwsagent.wroomagent.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import xwsagent.wroomagent.domain.BundledRequests;
import xwsagent.wroomagent.repository.BundleRepository;

@Service
@AllArgsConstructor
public class BundleService {

	private final BundleRepository bundleRepository;
	
	public BundledRequests save(BundledRequests b) {
		return this.bundleRepository.save(b);
	}
}
