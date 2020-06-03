package com.wroom.vehicleservice.controller;

import com.wroom.vehicleservice.feigns.AdsClient;
import com.wroom.vehicleservice.jwt.UserPrincipal;
import com.wroom.vehicleservice.producer.VehicleProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class HelloController {

    private final VehicleProducer vehicleProducer;
    private final AdsClient adsClient;

    public HelloController(VehicleProducer vehicleProducer, AdsClient adsClient) {
        this.vehicleProducer = vehicleProducer;
        this.adsClient = adsClient;
    }

    @GetMapping(value = "/hello")
    public ResponseEntity<?> hello(Authentication auth) throws UnknownHostException {
        //testira sinhronu komunikaciju sa drugom servisom
        //this.adsClient.hello(auth);
        //testira asinhronu komunikaciju sa drugom servisom
        this.vehicleProducer.send();
        System.out.println("I am reached.");
        UserPrincipal user = (UserPrincipal) auth.getPrincipal();
        System.out.println("My user from header username  " + user.getUsername());
        System.out.println("My user from header id  " + user.getId());
        for (GrantedAuthority authority : user.getAuthorities()) {
            System.out.println("Authorities " + authority.getAuthority());
        }
        String ip = InetAddress.getLocalHost().getHostAddress();

        return new ResponseEntity<>(String.format("Hello from vehicle service with ip address %s, my child.", ip), HttpStatus.OK);
    }
}