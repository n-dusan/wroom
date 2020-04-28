package xwsagent.wroomagent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xwsagent.wroomagent.dto.FuelTypeDTO;
import xwsagent.wroomagent.service.FuelTypeService;

@RestController
@RequestMapping(value="/api/fuel-type")
public class FuelTypeController {

	@Autowired
	private FuelTypeService fuelTypeService;
	
	@PostMapping(value = "/add", consumes = "application/json")
	public ResponseEntity<FuelTypeDTO> create(@RequestBody FuelTypeDTO fuelTypeDTO){
		if(fuelTypeService.create(fuelTypeDTO)) {
			return new ResponseEntity<>(fuelTypeDTO, HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>(fuelTypeDTO, HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(value = "/delete/{name}")
	public ResponseEntity<Void> delete(@PathVariable("name") String name){
		if(fuelTypeService.delete(name)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
