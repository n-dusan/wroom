package xwsagent.wroomagent.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

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
import xwsagent.wroomagent.converter.BrandTypeConverter;
import xwsagent.wroomagent.domain.BrandType;
import xwsagent.wroomagent.domain.dto.FeatureDTO;
import xwsagent.wroomagent.exception.APIError;
import xwsagent.wroomagent.service.BrandTypeService;

@RestController
@RequestMapping(value = EndpointConfig.BRAND_TYPE_BASE_URL)
public class BrandTypeController {
	

	private final BrandTypeService brandTypeService;

	public BrandTypeController(BrandTypeService brandTypeService) {
		this.brandTypeService = brandTypeService;
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> create(@Valid @RequestBody FeatureDTO brandTypeDTO){
		
		try {
			return new ResponseEntity<>(
					BrandTypeConverter.fromEntity(brandTypeService.save(BrandTypeConverter.toEntity(brandTypeDTO))),
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
		brandTypeService.delete(name);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(produces = "application/json")
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
