package xwsagent.wroomagent.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xwsagent.wroomagent.config.EndpointConfig;
import xwsagent.wroomagent.converter.GearboxTypeConverter;
import xwsagent.wroomagent.domain.GearboxType;
import xwsagent.wroomagent.domain.dto.FeatureDTO;
import xwsagent.wroomagent.exception.APIError;
import xwsagent.wroomagent.service.GearboxTypeService;

@RestController
@RequestMapping(value = EndpointConfig.GEARBOX_TYPE_BASE_URL)
public class GearboxTypeController {

	private final GearboxTypeService gearboxTypeService;

	public GearboxTypeController(GearboxTypeService gearboxTypeService) {
		this.gearboxTypeService = gearboxTypeService;
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> create(@Valid @RequestBody FeatureDTO gearboxTypeDTO){
		try {
			return new ResponseEntity<>(
					GearboxTypeConverter.fromEntity(gearboxTypeService.save(GearboxTypeConverter.toEntity(gearboxTypeDTO))),
					HttpStatus.CREATED
			);
		} catch(NullPointerException e) {
			List<String> errors = new ArrayList<String>();
			errors.add("Choosen name already exists!");
			return new ResponseEntity<>(new APIError(HttpStatus.BAD_REQUEST, "Not valid", errors), HttpStatus.BAD_REQUEST);
		}		
	}
	
	@DeleteMapping(value = "/{name}")
	public ResponseEntity<Void> delete(@PathVariable("name") String name){
		gearboxTypeService.delete(name);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<FeatureDTO>> getAll() {
		
		return new ResponseEntity<>(
				GearboxTypeConverter.fromEntityList(gearboxTypeService.getAll(), GearboxTypeConverter::fromEntity),
				HttpStatus.OK
		);
	}
	
	@PutMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<FeatureDTO> update(@RequestBody FeatureDTO featureDTO, @PathVariable("id")Long id){
		GearboxType gt = gearboxTypeService.findById(id);
		
		return new ResponseEntity<>(
				GearboxTypeConverter.fromEntity(gearboxTypeService.update(gt, featureDTO)),
				HttpStatus.OK
		);
	}
	
}
