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

import xwsagent.wroomagent.converter.GearboxTypeConverter;
import xwsagent.wroomagent.domain.GearboxType;
import xwsagent.wroomagent.domain.dto.FeatureDTO;
import xwsagent.wroomagent.service.GearboxTypeService;

@RestController
@RequestMapping(value = "api/gearbox-type")
public class GearboxTypeController {

	@Autowired
	private GearboxTypeService gearboxTypeService;
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<FeatureDTO> create(@RequestBody FeatureDTO gearboxTypeDTO){
		
		return new ResponseEntity<>(
			GearboxTypeConverter.fromEntity(gearboxTypeService.save(GearboxTypeConverter.toEntity(gearboxTypeDTO))),
			HttpStatus.CREATED
		);
	}
	
	@DeleteMapping(value = "/{name}")
	public ResponseEntity<Void> delete(@PathVariable("name") String name){
		gearboxTypeService.delete(name);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value = "/all", produces = "application/json")
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
