package com.wroom.vehicleservice.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wroom.vehicleservice.domain.Vehicle;
import com.wroom.vehicleservice.feigns.AdsClient;
import com.wroom.vehicleservice.jwt.UserPrincipal;
import com.wroom.vehicleservice.producer.VehicleProducer;
import com.wroom.vehicleservice.producer.message.EntityEnum;
import com.wroom.vehicleservice.producer.message.FeatureMessage;
import com.wroom.vehicleservice.producer.message.OperationEnum;
import com.wroom.vehicleservice.producer.message.VehicleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class HelloController {

    private final AdsClient adsClient;

    @Autowired
    private VehicleProducer vehicleProducer;

    public HelloController(AdsClient adsClient) {
        this.adsClient = adsClient;
    }

    @GetMapping(value = "/hello")
    public ResponseEntity<?> hello(Authentication auth, HttpServletRequest request) throws UnknownHostException {
        //testira sinhronu komunikaciju sa drugom servisom
        //String jwt = getJwtFromRequest(request);
        //System.out.println("my jwt " + jwt);

        //this.adsClient.hello(jwt);
        //testira asinhronu komunikaciju sa drugom servisom
        this.vehicleProducer.send(new VehicleMessage(1.3, 4, false, new FeatureMessage(1L, "test"), OperationEnum.CREATE, EntityEnum.BODY_TYPE));
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

    private String getJwtFromRequest(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
}