package com.wroom.zuulapigateway.feigns;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "vehicle-service")
public interface VehicleClient {

    @GetMapping("/hello")
    public ResponseEntity<String> hello();

}
