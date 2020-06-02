package xwsagent.wroomagent.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xwsagent.wroomagent.config.EndpointConfig;
import xwsagent.wroomagent.converter.VehicleConverter;
import xwsagent.wroomagent.domain.dto.VehicleDTO;
import xwsagent.wroomagent.converter.RentConverter;
import xwsagent.wroomagent.domain.dto.RentRequestDTO;
import xwsagent.wroomagent.jwt.UserPrincipal;
import xwsagent.wroomagent.service.RentsService;

@RestController
@RequestMapping(value = EndpointConfig.RENT_BASE_URL)
public class RentRequestController {

	private final RentsService rentsService;

	public RentRequestController(RentsService rentsService) {
		this.rentsService = rentsService;
	}
	
	
	@PostMapping
	public ResponseEntity<?> sendRequest(@RequestBody RentRequestDTO dto, Authentication auth) {
		try {
			return new ResponseEntity<>(
					RentConverter
							.fromEntity(this.rentsService.sendRequest(dto, ((UserPrincipal) auth.getPrincipal()).getId())),
					HttpStatus.CREATED);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}

	@PostMapping(value = "/occupy")
	public ResponseEntity<?> occupy(@RequestBody RentRequestDTO rentRequestDTO, Authentication auth) {
		if (rentsService.occupy(rentRequestDTO, auth)) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/all/{user}")
    public ResponseEntity<List<RentRequestDTO>> getAllUserOccupy(@PathVariable("user") Long userId) {
        return new ResponseEntity<>(rentsService.occupyList(userId),
                HttpStatus.OK);
    }
	

}
