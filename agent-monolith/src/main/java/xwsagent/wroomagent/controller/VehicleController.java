package xwsagent.wroomagent.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import xwsagent.wroomagent.domain.ErrorResponse;
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
	public Vehicle create(@Valid @RequestBody VehicleDTO vehicleDTO){
		Vehicle v = vehicleService.save(vehicleDTO);
		return v;
	}
	
	
	@PostMapping(value="/upload/{id}")
	public ResponseEntity<?> postImage(@RequestParam("file") List<MultipartFile> files,@PathVariable("id")Long id) throws IOException {
		Vehicle vehicle = vehicleService.findOne(id);
		vehicleService.postImages(files,vehicle);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        String s = ex.getLocalizedMessage();
        ErrorResponse error = new ErrorResponse(s, details);
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
