package xwsagent.wroomagent.controller;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


import java.util.List;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import xwsagent.wroomagent.converter.AdConverter;
import xwsagent.wroomagent.converter.VehicleConverter;
import xwsagent.wroomagent.config.EndpointConfig;

import xwsagent.wroomagent.domain.Image;
import xwsagent.wroomagent.domain.Vehicle;
import xwsagent.wroomagent.domain.dto.AdDTO;
import xwsagent.wroomagent.domain.dto.VehicleDTO;
import xwsagent.wroomagent.repository.VehicleRepository;
import xwsagent.wroomagent.service.ImageService;
import xwsagent.wroomagent.service.VehicleService;

@RestController
@RequestMapping(value = EndpointConfig.VEHICLE_BASE_URL)
public class VehicleController {

	private final VehicleRepository vehicleRepository;

	private final VehicleService vehicleService;
	private final ImageService imageService;

	public VehicleController(VehicleService vehicleService, ImageService imageService, VehicleRepository vehicleRepository) {
		this.vehicleService = vehicleService;
		this.imageService = imageService;
		this.vehicleRepository = vehicleRepository;
	}

	/**
	 * POST /api/vehicle
	 *
	 * @param vehicleDTO
	 * @param auth
	 * @return newly created vehicle
	 */
	@PostMapping(consumes = "application/json")
	public ResponseEntity<VehicleDTO> create(@Valid @RequestBody VehicleDTO vehicleDTO, Authentication auth) {
		System.out.println("DTO" + vehicleDTO);
		return new ResponseEntity<>(VehicleConverter.fromEntity(vehicleService.save(vehicleDTO, auth)), HttpStatus.OK);
	}


	@PostMapping(value = "/upload/{id}")
	public ResponseEntity<?> postImage(@RequestParam("file") List<MultipartFile> files, @PathVariable("id") Long id) {
		Vehicle vehicle = vehicleService.findById(id);
		vehicleService.postImages(files, vehicle);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<?> getVehicles(Authentication auth) {
		return new ResponseEntity<>(
				VehicleConverter.fromEntityList(vehicleService.getAllActive(auth), VehicleConverter::fromEntity),
				HttpStatus.OK
		);
	}
	
	@GetMapping("/all/{user}")
    public ResponseEntity<List<VehicleDTO>> getAllUser(@PathVariable("user") Long userId) {
        return new ResponseEntity<>(VehicleConverter.fromEntityList(vehicleService.findAllActiveForUser(userId), VehicleConverter::fromEntity),
                HttpStatus.OK);
    }
	
	@DeleteMapping(value = "/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
		System.out.println("Tu je stigao");
        vehicleService.delete(id);
        return new ResponseEntity<>("Vehicle deleted.", HttpStatus.OK);
    }
	
	@GetMapping(value = "/getImages/{id}")
	public List<byte[]> getFile(@PathVariable("id") Long id) throws IOException {
		List<Image> listImage = vehicleRepository.findByVehicle(vehicleService.findById(id));
		List<byte[]> arrays = new ArrayList<byte[]>();
		for (Image i : listImage) {
			Path path = Paths.get(i.getUrlPath());
			arrays.add(Files.readAllBytes(path));
		}
		return arrays;
	}
}
