package xwsagent.wroomagent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xwsagent.wroomagent.dto.ModelTypeDTO;
import xwsagent.wroomagent.service.ModelTypeService;

@RestController
@RequestMapping(value="api/model-type")
public class ModelTypeController {
	
	@Autowired
	private ModelTypeService modelTypeService;
	
	@PostMapping(value = "/add", consumes = "application/json")
	public ResponseEntity<ModelTypeDTO> create(@RequestBody ModelTypeDTO modelTypeDTO){
		if(modelTypeService.create(modelTypeDTO)) {
			return new ResponseEntity<>(modelTypeDTO, HttpStatus.OK);
	    }else {
	    	return new ResponseEntity<>(modelTypeDTO, HttpStatus.BAD_REQUEST);
	    }
	}
}
