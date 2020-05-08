package xwsagent.wroomagent.controller;

import java.util.List;

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

import xwsagent.wroomagent.domain.ModelType;
import xwsagent.wroomagent.domain.dto.ModelTypeDTO;
import xwsagent.wroomagent.service.ModelTypeService;

@RestController
@RequestMapping(value="api/model-type")
public class ModelTypeController {
	
	@Autowired
	private ModelTypeService modelTypeService;
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<ModelTypeDTO> create(@RequestBody ModelTypeDTO modelTypeDTO){
		if(modelTypeService.create(modelTypeDTO)) {
			return new ResponseEntity<>(modelTypeDTO, HttpStatus.CREATED);
	    }else {
	    	return new ResponseEntity<>(modelTypeDTO, HttpStatus.BAD_REQUEST);
	    }
	}
	
	@DeleteMapping(value = "/{name}")
	public ResponseEntity<Void> delete(@PathVariable("name") String name){
		if(modelTypeService.delete(name)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
	    	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
	}
	
	@GetMapping
	public ResponseEntity<List<ModelType>> getModelTypes(){
		List<ModelType> all = modelTypeService.findAll();
		
		return new ResponseEntity<>(all, HttpStatus.OK);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> update(@RequestBody ModelTypeDTO modelTypeDTO, @PathVariable("id")Long id){
		ModelType mt = modelTypeService.findById(id);
		if(this.modelTypeService.update(mt,modelTypeDTO)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
}
