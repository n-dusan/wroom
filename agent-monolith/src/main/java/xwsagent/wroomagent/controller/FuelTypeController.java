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
import xwsagent.wroomagent.domain.FuelType;
import xwsagent.wroomagent.dto.FuelTypeDTO;
import xwsagent.wroomagent.service.FuelTypeService;

@RestController
@RequestMapping(value="/api/fuel-type")
public class FuelTypeController {

	@Autowired
	private FuelTypeService fuelTypeService;
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<FuelTypeDTO> create(@RequestBody FuelTypeDTO fuelTypeDTO){
		if(fuelTypeService.create(fuelTypeDTO)) {
			return new ResponseEntity<>(fuelTypeDTO, HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>(fuelTypeDTO, HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(value = "/{name}")
	public ResponseEntity<Void> delete(@PathVariable("name") String name){
		if(fuelTypeService.delete(name)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping
	public ResponseEntity<List<FuelType>> getModelTypes(){
		List<FuelType> all = fuelTypeService.findAll();
		
		return new ResponseEntity<>(all, HttpStatus.OK);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> update(@RequestBody FuelTypeDTO fuelTypeDTO, @PathVariable("id")Long id){
		FuelType ft = fuelTypeService.findById(id);
		if(this.fuelTypeService.update(ft,fuelTypeDTO)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
