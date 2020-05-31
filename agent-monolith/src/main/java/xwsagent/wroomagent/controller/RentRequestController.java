package xwsagent.wroomagent.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xwsagent.wroomagent.config.EndpointConfig;
import xwsagent.wroomagent.domain.dto.RentRequestDTO;
import xwsagent.wroomagent.service.RentsService;

@RestController
@RequestMapping(value = EndpointConfig.RENT_BASE_URL)
public class RentRequestController {
	
	private final RentsService rentsService;
	
	public RentRequestController(RentsService rentsService) {
		this.rentsService = rentsService;
	}
	
	@PostMapping
	public ResponseEntity<?> occupy(@RequestBody RentRequestDTO rentRequestDTO, Authentication auth) {
	   if(rentsService.occupy(rentRequestDTO, auth)) {
		   return new ResponseEntity<>(HttpStatus.OK);
	   }else {
		   return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	   }
	   
	}
}
