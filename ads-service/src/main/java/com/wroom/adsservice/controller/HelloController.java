package com.wroom.adsservice.controller;

import com.wroom.adsservice.feigns.VehicleClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {

    @Autowired
    private VehicleClient vehicleClient;

    //testira i feignclient iz mikroservisa ka drugom mikroservisu, radi :D
    @GetMapping(value = "/hello")
    public ResponseEntity<?> hello() {
        System.out.println("I am reached.");
        System.out.println("Reaching out to vehicle, while I'm at it" + vehicleClient.hello());

        return new ResponseEntity<>(("Hello from ads service"), HttpStatus.OK);
    }
}