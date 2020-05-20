package com.wroom.vehicleservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class HelloController {

    @GetMapping(value = "/hello")
    public ResponseEntity<?> hello() throws UnknownHostException {
        System.out.println("I am reached.");
        String ip = InetAddress.getLocalHost().getHostAddress();
        return new ResponseEntity<>(String.format("Hello from vehicle service with ip address %s, my child.", ip), HttpStatus.OK);
    }
}