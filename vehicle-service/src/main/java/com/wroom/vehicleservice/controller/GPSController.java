package com.wroom.vehicleservice.controller;

import com.wroom.vehicleservice.config.EndpointConfig;
import com.wroom.vehicleservice.service.GPSService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = EndpointConfig.GPS_BASE_URL)
@Log4j2
public class GPSController {

    private final GPSService gpsService;

    public GPSController(GPSService gpsService) {
        this.gpsService = gpsService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> generate(@PathVariable("id") String id) {
        log.info("POST reached");
        return new ResponseEntity<>(gpsService.generateJWT(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<String> hello() {
        log.info("GET reached");
        return new ResponseEntity<>("Hello from gps!", HttpStatus.OK);
    }
}
