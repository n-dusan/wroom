package com.wroom.vehicleservice.controller;

import com.wroom.vehicleservice.config.EndpointConfig;
import com.wroom.vehicleservice.converter.VehicleConverter;
import com.wroom.vehicleservice.domain.Vehicle;
import com.wroom.vehicleservice.domain.dto.AdDTO;
import com.wroom.vehicleservice.domain.dto.VehicleDTO;
import com.wroom.vehicleservice.domain.dto.VehicleImageDTO;
import com.wroom.vehicleservice.feigns.AdsClient;
import com.wroom.vehicleservice.repository.VehicleRepository;
import com.wroom.vehicleservice.service.ImageService;
import com.wroom.vehicleservice.service.VehicleService;
import com.wroom.vehicleservice.utils.RequestCounter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = EndpointConfig.VEHICLE_BASE_URL)
@Log4j2
public class VehicleController {

    private static final String LOG_CREATE = "action=create user=%s ip_address=%s times=%s ";
    private static final String LOG_POST_IMAGE = "action=postImage user=%s ip_address=%s times=%s ";
    private static final String LOG_UPDATE = "action=update user=%s ip_address=%s times=%s ";

    private final VehicleRepository vehicleRepository;
    private final VehicleService vehicleService;
    private final RequestCounter requestCounter;
    private final AdsClient adsClient;

    public VehicleController(VehicleService vehicleService, VehicleRepository vehicleRepository, RequestCounter requestCounter, AdsClient adsClient) {
        this.vehicleService = vehicleService;
        this.vehicleRepository = vehicleRepository;
        this.requestCounter = requestCounter;
        this.adsClient = adsClient;
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
    public ResponseEntity<List<VehicleDTO>> getVehicles(Authentication auth) {
        return new ResponseEntity<>(
                VehicleConverter.fromEntityList(vehicleService.getAllActive(auth), VehicleConverter::fromEntity),
                HttpStatus.OK
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<VehicleDTO> findVehicleById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                VehicleConverter.fromEntity(vehicleService.findById(id)),
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
        // **** MORA DA KOMUNCIIRA SA AD SERVISOM
         List<AdDTO> ads = adsClient.findByVehicle(vehicle.getId());
         if(ads != null) {
             if(!ads.isEmpty()) {
                 return new ResponseEntity<>("Vehicle not deleted.", HttpStatus.BAD_REQUEST);
             }
         }
            vehicleService.delete(id);
            return new ResponseEntity<>("Vehicle deleted.", HttpStatus.OK);

    }


    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllVehicles() {
        return new ResponseEntity<>(
                VehicleConverter.fromEntityList(vehicleService.findAll(), VehicleConverter::fromEntity),
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