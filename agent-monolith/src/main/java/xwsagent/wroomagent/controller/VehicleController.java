package xwsagent.wroomagent.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import xwsagent.wroomagent.config.EndpointConfig;
import xwsagent.wroomagent.domain.Image;
import xwsagent.wroomagent.domain.Vehicle;
import xwsagent.wroomagent.domain.dto.VehicleDTO;
import xwsagent.wroomagent.service.ImageService;
import xwsagent.wroomagent.service.VehicleService;

@RestController
@RequestMapping(value = EndpointConfig.VEHICLE_BASE_URL)
public class VehicleController {

	private final VehicleService vehicleService;

	private final ImageService imageService;

	public VehicleController(VehicleService vehicleService, ImageService imageService) {
		this.vehicleService = vehicleService;
		this.imageService = imageService;
	}
	
	@PostMapping(consumes = "application/json")
	public Vehicle create(@RequestBody VehicleDTO vehicleDTO){
		Vehicle v = vehicleService.save(vehicleDTO);
		return v;
	}
	
	
	@PostMapping(value="/upload/{id}")
	public ResponseEntity<?> postImage(@RequestParam("file") List<MultipartFile> files,@PathVariable("id")Long id) throws IOException {
		Vehicle vehicle = vehicleService.findOne(id);
		vehicleService.postImages(files,vehicle);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
