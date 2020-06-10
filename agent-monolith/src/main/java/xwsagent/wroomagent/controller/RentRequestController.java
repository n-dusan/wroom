package xwsagent.wroomagent.controller;

import java.util.List;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;
import xwsagent.wroomagent.config.EndpointConfig;
import xwsagent.wroomagent.converter.BundleConverter;
import xwsagent.wroomagent.converter.RentConverter;
import xwsagent.wroomagent.domain.dto.RentRequestDTO;
import xwsagent.wroomagent.jwt.UserPrincipal;
import xwsagent.wroomagent.service.RentsService;
import xwsagent.wroomagent.util.RequestCounter;

import javax.validation.Valid;

@RestController
@RequestMapping(value = EndpointConfig.RENT_BASE_URL)
@Log4j2
public class RentRequestController {
	
	private static final String LOG_SEND_REQUEST = "action=sendRequest user=%s times=%s ";
	private static final String LOG_SEND_BUNDLED_REQUEST = "action=sendBundledRequest user=%s times=%s ";
	private static final String LOG_OCCUPY = "action=occupy user=%s times=%s ";

	private final RentsService rentsService;
	private final RequestCounter requestCounter;

	public RentRequestController(RentsService rentsService, RequestCounter requestCounter) {
		this.rentsService = rentsService;
		this.requestCounter = requestCounter;
	}
	
	
	@PostMapping
	public ResponseEntity<?> sendRequest(@RequestBody @Valid RentRequestDTO dto, Authentication auth) {
		String logContent = String.format(LOG_SEND_REQUEST, auth.getName(), requestCounter.get(EndpointConfig.RENT_BASE_URL));
		try {
			log.info(logContent);
			return new ResponseEntity<>(
					RentConverter
							.fromEntity(this.rentsService.sendRequest(dto, ((UserPrincipal) auth.getPrincipal()).getId())),
					HttpStatus.CREATED);
		} catch(Exception e) {
			log.error(logContent + "General exception");
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PostMapping(value="/bundle")
	public ResponseEntity<?> sendBundledRequest(@RequestBody RentRequestDTO[] dto, Authentication auth) {
		String logContent = String.format(LOG_SEND_BUNDLED_REQUEST, auth.getName(), requestCounter.get(EndpointConfig.RENT_BASE_URL));
		try {
			log.info(logContent);
			return new ResponseEntity<>(
					BundleConverter
							.fromEntity(this.rentsService.sendBundleRequest(dto, ((UserPrincipal) auth.getPrincipal()).getId())),
					HttpStatus.CREATED);
		} catch(Exception e) {
			log.error(logContent + "General exception");
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}

	@PostMapping(value = "/occupy")
	@PreAuthorize("hasAuthority('PHYSICALLY_RESERVE_VEHICLE') || hasAuthority('COMPLETE_ACCESS')")
	public ResponseEntity<?> occupy(@RequestBody RentRequestDTO rentRequestDTO, Authentication auth) {
		String logContent = String.format(LOG_OCCUPY, auth.getName(), requestCounter.get(EndpointConfig.RENT_BASE_URL));
		if (rentsService.occupy(rentRequestDTO, auth)) {
			log.info(logContent);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			log.error(logContent + "General exception");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/all/{user}")
    public ResponseEntity<List<RentRequestDTO>> getAllUserOccupy(@PathVariable("user") Long userId) {
        return new ResponseEntity<>(rentsService.occupyList(userId),
                HttpStatus.OK);
    }
	

}
