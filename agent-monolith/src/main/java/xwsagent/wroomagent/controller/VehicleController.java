package xwsagent.wroomagent.controller;


import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j2;

import xwsagent.wroomagent.converter.VehicleConverter;
import xwsagent.wroomagent.config.EndpointConfig;
import xwsagent.wroomagent.domain.Ad;
import xwsagent.wroomagent.domain.Vehicle;
import xwsagent.wroomagent.domain.dto.VehicleDTO;
import xwsagent.wroomagent.domain.dto.VehicleImageDTO;
import xwsagent.wroomagent.repository.VehicleRepository;
import xwsagent.wroomagent.service.ImageService;
import xwsagent.wroomagent.service.VehicleService;
import xwsagent.wroomagent.util.RequestCounter;

@RestController
@RequestMapping(value = EndpointConfig.VEHICLE_BASE_URL)
@Log4j2
public class VehicleController {
	
	private static final String LOG_CREATE = "action=create user=%s ip_address=%s times=%s ";
	private static final String LOG_POST_IMAGE = "action=postImage user=%s ip_address=%s times=%s ";
	private static final String LOG_UPDATE = "action=update user=%s ip_address=%s times=%s ";

	private final VehicleRepository vehicleRepository;
	private final VehicleService vehicleService;
	private final ImageService imageService;
	private final RequestCounter requestCounter;

	public VehicleController(VehicleService vehicleService, ImageService imageService, VehicleRepository vehicleRepository, RequestCounter requestCounter) {
		this.vehicleService = vehicleService;
		this.imageService = imageService;
		this.vehicleRepository = vehicleRepository;
		this.requestCounter = requestCounter;
	}

	/**
	 * POST /api/vehicle
	 *
	 * @param vehicleDTO
	 * @param auth
	 * @return newly created vehicle
	 */
	@PostMapping(consumes = "application/json")
	public ResponseEntity<VehicleDTO> create(@Valid @RequestBody VehicleDTO vehicleDTO, Authentication auth, HttpServletRequest httpServletRequest) {
		System.out.println("DTO" + vehicleDTO);
		String logContent = String.format(LOG_CREATE, auth.getName(), httpServletRequest.getRemoteAddr(), requestCounter.get(EndpointConfig.VEHICLE_BASE_URL));
		try {
			log.info(logContent);
			return new ResponseEntity<>(VehicleConverter.fromEntity(vehicleService.save(vehicleDTO, auth)), HttpStatus.OK);
		}catch(Exception e) {
			log.error(logContent + "General exception");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}


	@PostMapping(value = "/upload/{id}")
	public ResponseEntity<?> postImage(@RequestParam("file") List<MultipartFile> files, @PathVariable("id") Long id, Authentication auth, HttpServletRequest httpServletRequest) {
		Vehicle vehicle = vehicleService.findById(id);
		String logContent = String.format(LOG_POST_IMAGE, auth.getName(), httpServletRequest.getRemoteAddr(), requestCounter.get(EndpointConfig.VEHICLE_BASE_URL));
		try {
			log.info(logContent);
			vehicleService.postImages(files, vehicle);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e) {
			log.error(logContent + "General exception");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
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
		Vehicle vehicle = vehicleService.findById(id);
        List<Ad> ads = vehicleRepository.findByVehicleId(vehicle);
        if(ads.isEmpty() == true) {
        	vehicleService.delete(id);
        	return new ResponseEntity<>("Vehicle deleted.", HttpStatus.OK);
        }else {
        	return new ResponseEntity<>("Vehicle not deleted.", HttpStatus.BAD_REQUEST);
        }
        
    }
	
	
	@GetMapping(value = "/all")
	public ResponseEntity<?> getAllVehicles() {
		return new ResponseEntity<>(
				VehicleConverter.fromEntityList(vehicleService.findAll(), VehicleConverter::fromEntity),
				HttpStatus.OK
		);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getVehicle(@PathVariable("id") Long id) {
		return new ResponseEntity<>(
				VehicleConverter.fromEntity(vehicleService.findById(id)),
				HttpStatus.OK
		);
	}

	
	/**
	 * Used for search, attaches only one image for each vehicle,
	 * that is going to be shown on Search page.
	 * @return
	 */
	@GetMapping(value = "/with-image")
	public ResponseEntity<List<VehicleImageDTO>> getVehicleImage() throws IOException {
		try {
			return new ResponseEntity<>(vehicleService.getVehiclesImage(), HttpStatus.OK);
		} catch(Exception e) {
			System.out.println("Exception is thrown");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/getImages/{id}")
	public List<byte[]> getFile(@PathVariable("id") Long id) throws IOException {
		return vehicleService.getFile(id);
	}
	
	@PutMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<VehicleDTO> update(@RequestBody VehicleDTO vehicleDTO, @PathVariable("id")Long id, Authentication auth, HttpServletRequest httpServletRequest){
		Vehicle vehicle = vehicleService.findById(id);
		String logContent = String.format(LOG_UPDATE, auth.getName(), httpServletRequest.getRemoteAddr(), requestCounter.get(EndpointConfig.VEHICLE_BASE_URL));
		try {
			log.info(logContent);
			return new ResponseEntity<>(
					VehicleConverter.fromEntity(vehicleService.update(vehicle, vehicleDTO)),
					HttpStatus.OK
			);

		}catch(Exception e) {
			log.error(logContent + "General exception");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
			}
}
