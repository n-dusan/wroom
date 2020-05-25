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
import xwsagent.wroomagent.converter.BodyTypeConverter;
import xwsagent.wroomagent.domain.BodyType;
import xwsagent.wroomagent.domain.dto.FeatureDTO;
import xwsagent.wroomagent.exception.APIError;
import xwsagent.wroomagent.service.BodyTypeService;

@RestController
@RequestMapping(value = EndpointConfig.BODY_TYPE_BASE_URL)
public class BodyTypeController {

	private final BodyTypeService bodyTypeService;

	public BodyTypeController(BodyTypeService bodyTypeService) {
		this.bodyTypeService = bodyTypeService;
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> create(@Valid @RequestBody FeatureDTO featureDTO){

		try {
			return new ResponseEntity<>(
				    BodyTypeConverter.fromEntity(bodyTypeService.save(BodyTypeConverter.toEntity(featureDTO))),
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
		bodyTypeService.delete(name);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<FeatureDTO>> getBodyTypes(){
		
		return new ResponseEntity<>(
				BodyTypeConverter.fromEntityList(bodyTypeService.getAll(), BodyTypeConverter::fromEntity),
				HttpStatus.OK
		);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> update(@RequestBody FeatureDTO featureDTO, @PathVariable("id")Long id){
		BodyType bt = bodyTypeService.findById(id);
		
		return new ResponseEntity<>(
				BodyTypeConverter.fromEntity(bodyTypeService.update(bt, featureDTO)),
				HttpStatus.OK
		);
	}
}
