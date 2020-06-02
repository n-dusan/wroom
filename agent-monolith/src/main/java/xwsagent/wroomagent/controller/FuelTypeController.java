package xwsagent.wroomagent.controller;

import java.util.Collections;
import java.util.List;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xwsagent.wroomagent.config.EndpointConfig;
import xwsagent.wroomagent.converter.FuelTypeConverter;
import xwsagent.wroomagent.domain.FuelType;
import xwsagent.wroomagent.domain.dto.FeatureDTO;
import xwsagent.wroomagent.exception.APIError;
import xwsagent.wroomagent.jwt.UserPrincipal;
import xwsagent.wroomagent.service.FuelTypeService;
import xwsagent.wroomagent.util.RequestCounter;

import javax.validation.Valid;

@RestController
@RequestMapping(value = EndpointConfig.FUEL_TYPE_BASE_URL)
@Log4j2
public class FuelTypeController {

	private static final String LOG_CREATE = "action=create user=%s times=%s";
	private static final String LOG_UPDATE = "action=update user=%s times=%s";

	private final FuelTypeService fuelTypeService;
	private final RequestCounter requestCounter;

	public FuelTypeController(FuelTypeService fuelTypeService, RequestCounter requestCounter) {
		this.fuelTypeService = fuelTypeService;
		this.requestCounter = requestCounter;
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> create(@Valid @RequestBody FeatureDTO fuelTypeDTO, Authentication auth)  {
		String logContent = String.format(LOG_CREATE, ((UserPrincipal) auth.getPrincipal()).getUsername(), requestCounter.get(EndpointConfig.FUEL_TYPE_BASE_URL));

		try {
			log.info(logContent);
			return new ResponseEntity<>(
					FuelTypeConverter.fromEntity(fuelTypeService.save(FuelTypeConverter.toEntity(fuelTypeDTO))),
					HttpStatus.CREATED
			);
		} catch(NullPointerException e) {
			log.error(logContent);
			return new ResponseEntity<>(
					new APIError(HttpStatus.BAD_REQUEST, "Name exists", Collections.singletonList("Name exists")), HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(value = "/{name}")
	public ResponseEntity<Void> delete(@PathVariable("name") String name){
		fuelTypeService.delete(name);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<FeatureDTO>> getAll(){
		
		return new ResponseEntity<>(
				FuelTypeConverter.fromEntityList(fuelTypeService.getAll(), FuelTypeConverter::fromEntity),
				HttpStatus.OK
		);
	}
	
	@PutMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<FeatureDTO> update(@RequestBody FeatureDTO featureDTO, @PathVariable("id") Long id, Authentication auth){
		FuelType ft = fuelTypeService.findById(id);
		String logContent = String.format(LOG_UPDATE, ((UserPrincipal) auth.getPrincipal()).getUsername(), requestCounter.get(EndpointConfig.FUEL_TYPE_BASE_URL));
		log.info(logContent);
		return new ResponseEntity<>(
				FuelTypeConverter.fromEntity(fuelTypeService.update(ft, featureDTO)),
				HttpStatus.OK
		);
	}
}
