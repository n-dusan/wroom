package xwsagent.wroomagent.controller;

import java.util.List;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import lombok.extern.log4j.Log4j2;
import xwsagent.wroomagent.config.EndpointConfig;
import xwsagent.wroomagent.converter.BundleConverter;
import xwsagent.wroomagent.converter.RentConverter;
import xwsagent.wroomagent.domain.dto.RentRequestDTO;
import xwsagent.wroomagent.jwt.UserPrincipal;
import xwsagent.wroomagent.service.BundleService;
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
	private static final String LOG_DECLINE_REQUEST = "action=decline_request user=%s times=%s";
	private static final String LOG_ACCEPT_REQUEST = "action=accept_request user=%s times=%s";
	private static final String LOG_ACCEPT_BUNDLE = "action=accept_bundle user=%s times=%s";
	private static final String LOG_DECLINE_BUNDLE = "action=accept_bundle user=%s times=%s";

	private static final String LOG_PAY_BUNDLE = "action=accept_bundle user=%s times=%s";
	private static final String LOG_PAY_REQUEST = "action=accept_bundle user=%s times=%s";

	private final RentsService rentsService;
	private final RequestCounter requestCounter;
	private final BundleService bundleService;

	public RentRequestController(RentsService rentsService, RequestCounter requestCounter, BundleService bundleService) {
		this.rentsService = rentsService;
		this.bundleService = bundleService;
		this.requestCounter = requestCounter;
	}


	@PostMapping
	public ResponseEntity<?> sendRequest(@RequestBody RentRequestDTO dto, Authentication auth) {
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

	@PutMapping("/decline/{id}")
	public ResponseEntity<RentRequestDTO> decline(@PathVariable("id") Long id, Authentication auth) {
		String logContent = String.format(LOG_DECLINE_REQUEST, auth.getName(), requestCounter.get(EndpointConfig.RENT_BASE_URL));
		log.info(logContent);
		return new ResponseEntity<>(RentConverter.fromEntity(rentsService.decline(id)), HttpStatus.OK);
	}

	@PutMapping("/accept/{id}")
	public ResponseEntity<RentRequestDTO> accept(@PathVariable("id") Long id, Authentication auth) {
		String logContent = String.format(LOG_ACCEPT_REQUEST, auth.getName(), requestCounter.get(EndpointConfig.RENT_BASE_URL));
		log.info(logContent);
		return new ResponseEntity<>(RentConverter.fromEntity(rentsService.accept(id)), HttpStatus.OK);
	}


	@PutMapping("/bundle/decline/{id}")
	public ResponseEntity<List<RentRequestDTO>> declineBundle(@PathVariable("id") Long bundleId, Authentication auth) {
		String logContent = String.format(LOG_DECLINE_BUNDLE, auth.getName(), requestCounter.get(EndpointConfig.RENT_BASE_URL));
		log.info(logContent);
		return new ResponseEntity<>(RentConverter.fromEntityList(bundleService.decline(bundleId), RentConverter::fromEntity),
				HttpStatus.OK);
	}


	@PutMapping("/bundle/accept/{id}")
	public ResponseEntity<List<RentRequestDTO>> acceptBundle(@PathVariable("id") Long bundleId, Authentication auth) {
		String logContent = String.format(LOG_ACCEPT_BUNDLE, auth.getName(), requestCounter.get(EndpointConfig.RENT_BASE_URL));
		log.info(logContent);
		return new ResponseEntity<>(RentConverter.fromEntityList(bundleService.accept(bundleId), RentConverter::fromEntity),
				HttpStatus.OK);
	}

	@PutMapping("/bundle/pay/{id}")
	public ResponseEntity<List<RentRequestDTO>> payBundle(@PathVariable("id") Long bundleId, Authentication auth) {
		String logContent = String.format(LOG_PAY_BUNDLE, auth.getName(), requestCounter.get(EndpointConfig.RENT_BASE_URL));
		log.info(logContent);
		return new ResponseEntity<>(RentConverter.fromEntityList(bundleService.pay(bundleId), RentConverter::fromEntity),
				HttpStatus.OK);
	}

	@PutMapping("/pay/{id}")
	public ResponseEntity<RentRequestDTO> payRequest(@PathVariable("id") Long id, Authentication auth) {
		String logContent = String.format(LOG_PAY_REQUEST, auth.getName(), requestCounter.get(EndpointConfig.RENT_BASE_URL));
		log.info(logContent);
		return new ResponseEntity<>(RentConverter.fromEntity(rentsService.pay(id)),
				HttpStatus.OK);
	}

	@GetMapping("/pending/{user}")
	public ResponseEntity<List<RentRequestDTO>> getPending(@PathVariable("user") Long userId) {
		return new ResponseEntity<>(RentConverter.fromEntityList(rentsService.getPending(userId), RentConverter::fromEntity),
				HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RentRequestDTO> getOne(@PathVariable("id") Long id) {
		return new ResponseEntity<>(RentConverter.fromEntity(rentsService.findById(id)), HttpStatus.OK);
	}

	@GetMapping("/all/{user}")
	public ResponseEntity<List<RentRequestDTO>> getAllUserOccupy(@PathVariable("user") Long userId) {
		return new ResponseEntity<>(rentsService.occupyList(userId),
				HttpStatus.OK);
	}


	@GetMapping("/bundle/{id}")
	public ResponseEntity<List<RentRequestDTO>> getBundles(@PathVariable("id") Long id) {
		return new ResponseEntity<>(RentConverter.fromEntityList(bundleService.findBundledRentRequests(id),
				RentConverter::fromEntity), HttpStatus.OK);
	}

	@GetMapping("/requested/{user}")
	public ResponseEntity<List<RentRequestDTO>> getRequested(@PathVariable("user") Long userId) {
		return new ResponseEntity<>(RentConverter.fromEntityList(rentsService.findByRequestedUser(userId), RentConverter::fromEntity) ,
				HttpStatus.OK);
	}

	@GetMapping("/findByAd/{id}")
	public List<RentRequestDTO> findByAd(@PathVariable("id") Long id) {
		return this.rentsService.findByAd(id);
	}

	

}
