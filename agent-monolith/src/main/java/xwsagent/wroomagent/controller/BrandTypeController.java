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

import xwsagent.wroomagent.converter.BrandTypeConverter;
import xwsagent.wroomagent.converter.ModelTypeConverter;
import xwsagent.wroomagent.domain.BrandType;
import xwsagent.wroomagent.domain.dto.FeatureDTO;
import xwsagent.wroomagent.service.BrandTypeService;

@RestController
@RequestMapping(value="api/brand-type")
public class BrandTypeController {
	
	@Autowired
	private BrandTypeService brandTypeService;
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<FeatureDTO> create(@RequestBody FeatureDTO brandTypeDTO){
		
		return new ResponseEntity<>(
				BrandTypeConverter.fromEntity(brandTypeService.save(BrandTypeConverter.toEntity(brandTypeDTO))),
				HttpStatus.CREATED
		);
	}
	
	@DeleteMapping(value = "/{name}")
	public ResponseEntity<Void> delete(@PathVariable("name") String name){
		brandTypeService.delete(name);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value="/all", produces = "application/json")
	public ResponseEntity<List<FeatureDTO>> getModelTypes(){
		
		return new ResponseEntity<>(
				BrandTypeConverter.fromEntityList(brandTypeService.getAll(), BrandTypeConverter::fromEntity),
				HttpStatus.OK
		);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> update(@RequestBody FeatureDTO featureDTO, @PathVariable("id")Long id){
		BrandType bt = brandTypeService.findById(id);
		
		return new ResponseEntity<>(
			BrandTypeConverter.fromEntity(brandTypeService.update(bt, featureDTO)),
			HttpStatus.OK
		);
	}
}
