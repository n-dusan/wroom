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
import xwsagent.wroomagent.domain.BrandType;
import xwsagent.wroomagent.domain.dto.BrandTypeDTO;
import xwsagent.wroomagent.service.BrandTypeService;

@RestController
@RequestMapping(value="api/brand-type")
public class BrandTypeController {
	
	@Autowired
	private BrandTypeService brandTypeService;
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<BrandTypeDTO> create(@RequestBody BrandTypeDTO brandTypeDTO){
		if(brandTypeService.create(brandTypeDTO)) {
			return new ResponseEntity<>(brandTypeDTO, HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>(brandTypeDTO, HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(value = "/{name}")
	public ResponseEntity<Void> delete(@PathVariable("name") String name){
		if(brandTypeService.delete(name)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping
	public ResponseEntity<List<BrandType>> getModelTypes(){
		List<BrandType> all = brandTypeService.findAll();
		
		return new ResponseEntity<>(all, HttpStatus.OK);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> update(@RequestBody BrandTypeDTO brandTypeDTO, @PathVariable("id")Long id){
		BrandType bt = brandTypeService.findById(id);
		if(this.brandTypeService.update(bt,brandTypeDTO)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
