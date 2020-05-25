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

import xwsagent.wroomagent.converter.ModelTypeConverter;
import xwsagent.wroomagent.domain.ModelType;
import xwsagent.wroomagent.domain.dto.FeatureDTO;
import xwsagent.wroomagent.exception.APIError;
import xwsagent.wroomagent.service.ModelTypeService;

@RestController
@RequestMapping(value="api/model-type")
public class ModelTypeController {
	
	@Autowired
	private ModelTypeService modelTypeService;
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> create(@Valid @RequestBody FeatureDTO modelTypeDTO){

		try {
			return new ResponseEntity<>(
					ModelTypeConverter.fromEntity(modelTypeService.save(ModelTypeConverter.toEntity(modelTypeDTO))),
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
		modelTypeService.delete(name);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<FeatureDTO>> getAll(){
		
		return new ResponseEntity<>(
				ModelTypeConverter.fromEntityList(modelTypeService.getAll(), ModelTypeConverter::fromEntity),
				HttpStatus.OK
		);
	}
	
	@PutMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<FeatureDTO> update(@RequestBody FeatureDTO featureDTO, @PathVariable("id")Long id){
		ModelType mt = modelTypeService.findById(id);
		
		return new ResponseEntity<>(
				ModelTypeConverter.fromEntity(modelTypeService.update(mt, featureDTO)),
				HttpStatus.OK
		);
	}
	
}
