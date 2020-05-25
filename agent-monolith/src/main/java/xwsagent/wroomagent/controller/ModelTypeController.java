package xwsagent.wroomagent.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import xwsagent.wroomagent.converter.ModelTypeConverter;
import xwsagent.wroomagent.domain.ModelType;
import xwsagent.wroomagent.domain.dto.FeatureDTO;
import xwsagent.wroomagent.service.ModelTypeService;

@RestController
@RequestMapping(value = EndpointConfig.MODEL_TYPE_BASE_URL)
public class ModelTypeController {

	private final ModelTypeService modelTypeService;

	public ModelTypeController(ModelTypeService modelTypeService) {
		this.modelTypeService = modelTypeService;
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<FeatureDTO> create(@RequestBody FeatureDTO modelTypeDTO){

		return new ResponseEntity<>(
				ModelTypeConverter.fromEntity(modelTypeService.save(ModelTypeConverter.toEntity(modelTypeDTO))),
				HttpStatus.CREATED
		);
				
	}
	
	@DeleteMapping(value = "/{name}")
	public ResponseEntity<Void> delete(@PathVariable("name") String name){
		modelTypeService.delete(name);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value="/all", produces = "application/json")
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
