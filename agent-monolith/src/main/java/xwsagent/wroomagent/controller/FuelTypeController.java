package xwsagent.wroomagent.controller;

import java.util.List;

import lombok.extern.log4j.Log4j2;
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
import xwsagent.wroomagent.converter.FuelTypeConverter;
import xwsagent.wroomagent.domain.FuelType;
import xwsagent.wroomagent.domain.dto.FeatureDTO;
import xwsagent.wroomagent.service.FuelTypeService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = EndpointConfig.FUEL_TYPE_BASE_URL)
public class FuelTypeController {

	private final FuelTypeService fuelTypeService;

	public FuelTypeController(FuelTypeService fuelTypeService) {
		this.fuelTypeService = fuelTypeService;
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<FeatureDTO> create(@Valid @RequestBody FeatureDTO fuelTypeDTO)  {
		return new ResponseEntity<>(
				FuelTypeConverter.fromEntity(fuelTypeService.save(FuelTypeConverter.toEntity(fuelTypeDTO))),
				HttpStatus.CREATED
		);
	}
	
	@DeleteMapping(value = "/{name}")
	public ResponseEntity<Void> delete(@PathVariable("name") String name){
		fuelTypeService.delete(name);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value="/all", produces = "application/json")
	public ResponseEntity<List<FeatureDTO>> getAll(){
		
		return new ResponseEntity<>(
				FuelTypeConverter.fromEntityList(fuelTypeService.getAll(), FuelTypeConverter::fromEntity),
				HttpStatus.OK
		);
	}
	
	@PutMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<FeatureDTO> update(@RequestBody FeatureDTO featureDTO, @PathVariable("id")Long id){
		FuelType ft = fuelTypeService.findById(id);
		
		return new ResponseEntity<>(
				FuelTypeConverter.fromEntity(fuelTypeService.update(ft, featureDTO)),
				HttpStatus.OK
		);
	}
}
