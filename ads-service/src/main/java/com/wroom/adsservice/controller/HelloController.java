package com.wroom.adsservice.controller;
import com.wroom.adsservice.jwt.UserPrincipal;
import com.wroom.adsservice.producer.AdsProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {

    @Autowired
    private AdsProducer adsProducer;

    @GetMapping(value = "/hello")
    public ResponseEntity<?> hello(Authentication auth) {
        UserPrincipal user = (UserPrincipal) auth.getPrincipal();
        System.out.println("My user from header username  " + user.getUsername());
        System.out.println("My user from header id  " + user.getId());
        for (GrantedAuthority authority : user.getAuthorities()) {
            System.out.println("Authorities " + authority.getAuthority());
        }
        System.out.println("I am reached.");
        adsProducer.send();
        return new ResponseEntity<>(("Hello from ads service"), HttpStatus.OK);
    }
}