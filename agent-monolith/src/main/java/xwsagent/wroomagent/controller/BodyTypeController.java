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

import xwsagent.wroomagent.domain.BodyType;
import xwsagent.wroomagent.domain.dto.BodyTypeDTO;
import xwsagent.wroomagent.service.BodyTypeService;

@RestController
@RequestMapping(value="api/body-type")
public class BodyTypeController {
	
	@Autowired
	private BodyTypeService bodyTypeService;
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<BodyTypeDTO> create(@RequestBody BodyTypeDTO bodyTypeDTO){
		if(bodyTypeService.create(bodyTypeDTO)) {
			return new ResponseEntity<>(bodyTypeDTO, HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>(bodyTypeDTO, HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(value = "/{name}")
	public ResponseEntity<Void> delete(@PathVariable("name") String name){
		if(bodyTypeService.delete(name)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping
	public ResponseEntity<List<BodyType>> getBodyTypes(){
		List<BodyType> all = bodyTypeService.findAll();
		
		return new ResponseEntity<>(all, HttpStatus.OK);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> update(@RequestBody BodyTypeDTO bodyTypeDTO, @PathVariable("id")Long id){
		BodyType bt = bodyTypeService.findById(id);
		if(this.bodyTypeService.update(bt,bodyTypeDTO)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
