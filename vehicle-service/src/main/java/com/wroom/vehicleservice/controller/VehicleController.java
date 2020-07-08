package com.wroom.vehicleservice.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.wroom.vehicleservice.config.EndpointConfig;
import com.wroom.vehicleservice.converter.VehicleConverter;
import com.wroom.vehicleservice.domain.Vehicle;
import com.wroom.vehicleservice.domain.dto.VehicleDTO;
import com.wroom.vehicleservice.domain.dto.VehicleImageDTO;
import com.wroom.vehicleservice.service.VehicleService;
import com.wroom.vehicleservice.utils.RequestCounter;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(value = EndpointConfig.VEHICLE_BASE_URL)
@Log4j2
public class VehicleController {

	private static final String LOG_CREATE = "action=create user=%s ip_address=%s times=%s ";
	private static final String LOG_POST_IMAGE = "action=postImage user=%s ip_address=%s times=%s ";
	private static final String LOG_UPDATE = "action=update user=%s ip_address=%s times=%s ";

	private final VehicleService vehicleService;
	private final RequestCounter requestCounter;

	public VehicleController(VehicleService vehicleService, RequestCounter requestCounter) {
		this.vehicleService = vehicleService;
		this.requestCounter = requestCounter;
	}

	/**
	 * POST /api/vehicle
	 * 
	 * Creates a new vehicle. After creation, notifies search-service to update its
	 * database.
	 *
	 * @param vehicleDTO
	 * @param auth
	 * @return newly created vehicle
	 */
	@PostMapping(consumes = "application/json")
	@PreAuthorize("(hasAuthority('CRUD_VEHICLE') || hasAuthority('COMPLETE_ACCESS')) && hasAuthority('ROLE_CRUD_VEHICLE')")
	public ResponseEntity<VehicleDTO> create(@Valid @RequestBody VehicleDTO vehicleDTO, Authentication auth,
			HttpServletRequest httpServletRequest) {
		System.out.println("DTO" + vehicleDTO);
		String logContent = String.format(LOG_CREATE, auth.getName(), httpServletRequest.getRemoteAddr(),
				requestCounter.get(EndpointConfig.VEHICLE_BASE_URL));
		
		log.info(logContent);
		return new ResponseEntity<>(VehicleConverter.fromEntity(vehicleService.save(vehicleDTO, auth)), HttpStatus.OK);
		

	}

	/**
	 * Uploading single image and saving it to static folder
	 * 
	 * @param files
	 * @param id
	 * @param auth
	 * @param httpServletRequest
	 * @return
	 */
	// TODO: Change path to "/image/upload/{id}"
	@PostMapping(value = "/upload/{id}")
	@PreAuthorize("(hasAuthority('UPLOAD_IMAGES') || hasAuthority('CRUD_VEHICLE') || hasAuthority('COMPLETE_ACCESS')) && hasAuthority('ROLE_CRUD_VEHICLE') ")
	public ResponseEntity<?> postImage(@RequestParam("file") List<MultipartFile> files, @PathVariable("id") Long id,
			Authentication auth, HttpServletRequest httpServletRequest) {
		Vehicle vehicle = vehicleService.findById(id);
		String logContent = String.format(LOG_POST_IMAGE, auth.getName(), httpServletRequest.getRemoteAddr(),
				requestCounter.get(EndpointConfig.VEHICLE_BASE_URL));
		try {
			log.info(logContent);
			vehicleService.postImages(files, vehicle);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			log.error(logContent + "General exception");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * Fetch all vehicles from currently logged user
	 * 
	 * @param auth
	 * @return
	 */
	@GetMapping
	public ResponseEntity<List<VehicleDTO>> getVehicles(Authentication auth) {
		return new ResponseEntity<>(
				VehicleConverter.fromEntityList(vehicleService.getAllActive(auth), VehicleConverter::fromEntity),
				HttpStatus.OK);
	}



	/**
	 * Fetch one specific vehicle
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<VehicleDTO> findVehicleById(@PathVariable("id") Long id) {
		return new ResponseEntity<>(VehicleConverter.fromEntity(vehicleService.findById(id)), HttpStatus.OK);
	}

	// TODO: Ova metoda radi isto sto i gore definisana pod @GetMapping getVehicles.
	// Videti sta se koristi na frontu i obrisati onu koja je suvisna
	@GetMapping("/all/{user}")
	public ResponseEntity<List<VehicleDTO>> getAllUser(@PathVariable("user") Long userId) {
		return new ResponseEntity<>(VehicleConverter.fromEntityList(vehicleService.findAllActiveForUser(userId),
				VehicleConverter::fromEntity), HttpStatus.OK);
	}

	/**
	 * Delete one specific vehicle.
	 * 
	 * Before deleting, checks with ad-service if an ad with that vehicle exists.
	 * Vehicle cannot be deleted if there is an active ad with it.
	 * 
	 * After deleting, notifies search-service to update its database.
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
	@PreAuthorize("(hasAuthority('CRUD_VEHICLE') || hasAuthority('COMPLETE_ACCESS')) && hasAuthority('ROLE_CRUD_VEHICLE') ")
	public ResponseEntity<String> delete(@PathVariable("id") Long id) {
//		Vehicle vehicle = vehicleService.findById(id);
		// **** MORA DA KOMUNCIIRA SA AD SERVISOM
//		List<AdDTO> ads = adsClient.findByVehicle(vehicle.getId());
//		if (ads != null) {
//			if (!ads.isEmpty()) {
//				return new ResponseEntity<>("Vehicle not deleted.", HttpStatus.BAD_REQUEST);
//			}
//		}
		vehicleService.delete(id);
		return new ResponseEntity<>("Vehicle deleted.", HttpStatus.OK);

	}

	/**
	 * Fetch all existing vehicles that are not deleted.
	 * 
	 * @return
	 */
	@GetMapping(value = "/all")
	public ResponseEntity<?> getAllVehicles() {
		return new ResponseEntity<>(
				VehicleConverter.fromEntityList(vehicleService.findAll(), VehicleConverter::fromEntity), HttpStatus.OK);
	}

	/**
	 * Used for search, attaches only one image for each vehicle, that is going to
	 * be shown on Search page.
	 * 
	 * @return
	 */
	@GetMapping(value = "/with-image")
	public ResponseEntity<List<VehicleImageDTO>> getVehicleImage() throws IOException {
		try {
			return new ResponseEntity<>(vehicleService.getVehiclesImage(), HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("Exception is thrown");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Gets all images for given vehicle
	 * 
	 * @param id - ID value of vehicle that we need images for
	 * @return
	 * @throws IOException
	 */
	// TODO: change path to "/image/{id}"
	@GetMapping(value = "/getImages/{id}")
	public List<byte[]> getFile(@PathVariable("id") Long id) throws IOException {
		return vehicleService.getFile(id);
	}

	/**
	 * Updates a vehicle. After update notifies search-service to update its
	 * database.
	 * 
	 * @param vehicleDTO
	 * @param id
	 * @param auth
	 * @param httpServletRequest
	 * @return
	 */
	@PutMapping(value = "/{id}", produces = "application/json")
	@PreAuthorize("(hasAuthority('CRUD_VEHICLE') || hasAuthority('COMPLETE_ACCESS')) && hasAuthority('ROLE_CRUD_VEHICLE')")
	public ResponseEntity<VehicleDTO> update(@RequestBody VehicleDTO vehicleDTO, @PathVariable("id") Long id,
			Authentication auth, HttpServletRequest httpServletRequest) {
		String logContent = String.format(LOG_UPDATE, auth.getName(), httpServletRequest.getRemoteAddr(),
				requestCounter.get(EndpointConfig.VEHICLE_BASE_URL));
		try {
			log.info(logContent);
			return new ResponseEntity<>(VehicleConverter.fromEntity(vehicleService.update(vehicleDTO)),
					HttpStatus.OK);

		} catch (Exception e) {
			log.error(logContent + "General exception");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	//list for admin
	@GetMapping("/allVehicles")
	public ResponseEntity<?> getVehicles() {
		return new ResponseEntity<>(
			VehicleConverter.fromEntityList(vehicleService.findAll(), VehicleConverter::fromEntity),
			HttpStatus.OK);
	}
}