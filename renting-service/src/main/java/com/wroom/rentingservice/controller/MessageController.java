package com.wroom.rentingservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wroom.rentingservice.config.EndpointConfig;
import com.wroom.rentingservice.domain.dto.RentRequestDTO;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(value = EndpointConfig.MESSAGE_BASE_URL)
@Log4j2
public class MessageController {

//	private static final String LOG_SEND_REQUEST = "action=sendRequest user=%s times=%s ";
	
//	@PostMapping
//	public ResponseEntity<?> send(@RequestBody RentRequestDTO dto, Authentication auth) {
//		
//	}
	
}
