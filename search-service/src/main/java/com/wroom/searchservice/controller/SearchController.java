package com.wroom.searchservice.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wroom.searchservice.config.EndpointConfig;
import com.wroom.searchservice.converter.AdConverter;
import com.wroom.searchservice.domain.dto.SearchCriteriaDTO;
import com.wroom.searchservice.service.SearchService;

@RestController
@RequestMapping(value = EndpointConfig.SEARCH_BASE_URL)
public class SearchController {

	private final SearchService searchService;
	
    public SearchController(SearchService searchService) {
		this.searchService = searchService;
	}

	@GetMapping("/hello")
    public ResponseEntity<?> get() throws UnknownHostException {
        System.out.println("I am reached.");
        String ip = InetAddress.getLocalHost().getHostAddress();
        return new ResponseEntity<>(String.format("Hello from search service with ip address %s, my child.", ip), HttpStatus.OK);
    }
    
    /**
	 * POST /api/ads/search
	 * 
	 * @param search
	 * @return
	 */
	@PostMapping("/search")
	public ResponseEntity<?> search(@RequestBody SearchCriteriaDTO search) {
		System.out.println(">>> I'm searching...");
		return new ResponseEntity<>(
				AdConverter.fromEntityList(this.searchService.search(search), AdConverter::fromEntity), HttpStatus.OK);
	}
}
