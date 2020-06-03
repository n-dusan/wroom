package com.wroom.vehicleservice.feigns;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "ads-service")
public interface AdsClient {
    @GetMapping("/hello")
    public ResponseEntity<String> hello(@RequestHeader("Authorization") Authentication auth);
}
