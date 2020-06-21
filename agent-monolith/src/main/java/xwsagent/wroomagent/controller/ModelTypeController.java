package xwsagent.wroomagent.controller;

import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import xwsagent.wroomagent.converter.ModelTypeConverter;
import xwsagent.wroomagent.domain.ModelType;
import xwsagent.wroomagent.domain.dto.FeatureDTO;
import xwsagent.wroomagent.exception.APIError;
import xwsagent.wroomagent.jwt.UserPrincipal;
import xwsagent.wroomagent.service.ModelTypeService;
import xwsagent.wroomagent.util.RequestCounter;

@RestController
@RequestMapping(value = EndpointConfig.MODEL_TYPE_BASE_URL)
@Log4j2
public class ModelTypeController {

	private static final String LOG_CREATE = "action=create user=%s times=%s";
	private static final String LOG_UPDATE = "action=update user=%s times=%s";

	private final ModelTypeService modelTypeService;
	private final RequestCounter requestCounter;

	public ModelTypeController(ModelTypeService modelTypeService, RequestCounter requestCounter) {
		this.modelTypeService = modelTypeService;
		this.requestCounter = requestCounter;
	}

	@PreAuthorize("hasAuthority('MANAGE_VEHICLE_FEATURES')")
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> create(@Valid @RequestBody FeatureDTO modelTypeDTO, Authentication auth){
		String logContent = String.format(LOG_CREATE, ((UserPrincipal) auth.getPrincipal()).getUsername(), requestCounter.get(EndpointConfig.MODEL_TYPE_BASE_URL));

		try {
			log.info(logContent);
			return new ResponseEntity<>(
					ModelTypeConverter.fromEntity(modelTypeService.save(modelTypeDTO)),
					HttpStatus.CREATED 
			);
		} catch(NullPointerException e) {
			log.error(logContent);
			return new ResponseEntity<>(
					new APIError(HttpStatus.BAD_REQUEST, "Name exists", Collections.singletonList("Name exists")), HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasAuthority('MANAGE_VEHICLE_FEATURES')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id){
		modelTypeService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<FeatureDTO>> getAll(){
		
		return new ResponseEntity<>(
				ModelTypeConverter.fromEntityList(modelTypeService.getAll(), ModelTypeConverter::fromEntity),
				HttpStatus.OK
		);
	}

	@PreAuthorize("hasAuthority('MANAGE_VEHICLE_FEATURES')")
	@PutMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<FeatureDTO> update(@RequestBody FeatureDTO featureDTO, @PathVariable("id") Long id, Authentication auth){
		ModelType mt = modelTypeService.findById(id);
		String logContent = String.format(LOG_UPDATE, ((UserPrincipal) auth.getPrincipal()).getUsername(), requestCounter.get(EndpointConfig.MODEL_TYPE_BASE_URL));
		log.info(logContent);
		return new ResponseEntity<>(
				ModelTypeConverter.fromEntity(modelTypeService.update(mt, featureDTO)),
				HttpStatus.OK
		);
	}
	
}
