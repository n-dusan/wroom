package xwsagent.wroomagent.controller;

import java.util.List;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xwsagent.wroomagent.converter.VehicleConverter;
import xwsagent.wroomagent.config.EndpointConfig;

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

	/**
	 * POST /api/vehicle
	 * @param vehicleDTO
	 * @param auth
	 * @return newly created vehicle
	 */
	@PostMapping(consumes = "application/json")
	public ResponseEntity<VehicleDTO> create(@Valid @RequestBody VehicleDTO vehicleDTO, Authentication auth){
		System.out.println("DTO"+vehicleDTO);
		return new ResponseEntity<>(VehicleConverter.fromEntity(vehicleService.save(vehicleDTO, auth)), HttpStatus.OK);
	}
	
	
	@PostMapping(value="/upload/{id}")
	public ResponseEntity<?> postImage(@RequestParam("file") List<MultipartFile> files, @PathVariable("id") Long id) {
		Vehicle vehicle = vehicleService.findOne(id);
		vehicleService.postImages(files,vehicle);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<?> getVehicles(Authentication auth) {
		return new ResponseEntity<>(
				VehicleConverter.fromEntityList(vehicleService.getAllActive(auth), VehicleConverter::fromEntity),
				HttpStatus.OK
		);
	}


//	 @GetMapping(produces = "application/json")
//	 public ResponseEntity<List<VehicleDTO>> getAll() {
//	    return new ResponseEntity<>(
//	          VehicleConverter.fromEntityList(vehicleService.getAll(), VehicleConverter::fromEntity),
//	          HttpStatus.OK
//	    );
//	 }
}
