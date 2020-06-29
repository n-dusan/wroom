package com.wroom.vehicleservice.controller;

import com.wroom.vehicleservice.config.EndpointConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = EndpointConfig.GPS_BASE_URL)
@Log4j2
public class GPSController {

    @PostMapping
    public ResponseEntity<String> generate() {
        return new ResponseEntity<>("Generated jwt!", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("Hello from gps!", HttpStatus.OK);
    }
}
