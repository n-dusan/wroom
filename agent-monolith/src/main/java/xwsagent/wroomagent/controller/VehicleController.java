package xwsagent.wroomagent.controller;

import java.util.List;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
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
	public ResponseEntity<?> getVehicles() {
		return new ResponseEntity<>(
				VehicleConverter.fromEntityList(vehicleService.getAllActive(), VehicleConverter::fromEntity),
				HttpStatus.OK
		);
	}

	//ne znam za sta ti ovo treba
//	@ExceptionHandler(Exception.class)
//    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
//        List<String> details = new ArrayList<>();
//        details.add(ex.getLocalizedMessage());
//        String s = ex.getLocalizedMessage();
//        ErrorResponse error = new ErrorResponse(s, details);
//        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
