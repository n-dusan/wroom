package com.wroom.rentingservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping(value = "/hello")
    public ResponseEntity<?> hello() {
        System.out.println("I am reached.");
        return new ResponseEntity<>(String.format("Hello from RENTING service"), HttpStatus.OK);
    }
}