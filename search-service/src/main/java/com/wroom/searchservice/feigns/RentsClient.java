package com.wroom.searchservice.feigns;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.wroom.searchservice.domain.dto.RentRequestDTO;

@FeignClient(name = "renting-service")
public interface RentsClient {

	@GetMapping("/rents/findByAd/{id}")
	public List<RentRequestDTO> findByAd(@PathVariable("id") Long id);
	
}
