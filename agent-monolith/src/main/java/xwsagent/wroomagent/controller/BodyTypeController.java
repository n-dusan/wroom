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

import xwsagent.wroomagent.converter.BodyTypeConverter;
import xwsagent.wroomagent.domain.BodyType;
import xwsagent.wroomagent.domain.dto.FeatureDTO;
import xwsagent.wroomagent.service.BodyTypeService;

@RestController
@RequestMapping(value="api/body-type")
public class BodyTypeController {
	
	@Autowired
	private BodyTypeService bodyTypeService;
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<FeatureDTO> create(@RequestBody FeatureDTO featureDTO){

		return new ResponseEntity<>(
				BodyTypeConverter.fromEntity(bodyTypeService.save(BodyTypeConverter.toEntity(featureDTO))),
				HttpStatus.CREATED
		);
	}
	
	@DeleteMapping(value = "/{name}")
	public ResponseEntity<Void> delete(@PathVariable("name") String name){
		bodyTypeService.delete(name);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value="/all", produces = "application/json")
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
