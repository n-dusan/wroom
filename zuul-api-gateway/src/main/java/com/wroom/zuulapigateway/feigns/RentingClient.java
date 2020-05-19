package com.wroom.zuulapigateway.feigns;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "renting-service")
public interface RentingClient {

    @GetMapping("/hello")
    public ResponseEntity<String> hello();
}
