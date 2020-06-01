package com.wroom.vehicleservice.controller;

import com.wroom.vehicleservice.jwt.UserPrincipal;
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

    @GetMapping(value = "/hello")
    public ResponseEntity<?> hello(Authentication auth) throws UnknownHostException {
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