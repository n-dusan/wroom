package com.wroom.searchservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class SearchController {

    @GetMapping("/hello")
    public ResponseEntity<?> get() throws UnknownHostException {
        System.out.println("I am reached.");
        String ip = InetAddress.getLocalHost().getHostAddress();
        return new ResponseEntity<>(String.format("Hello from search service with ip address %s, my child.", ip), HttpStatus.OK);
    }
}
