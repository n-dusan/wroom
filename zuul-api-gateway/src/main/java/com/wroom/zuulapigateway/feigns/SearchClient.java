package com.wroom.zuulapigateway.feigns;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "search-service")
public interface SearchClient {

    @GetMapping("/hello")
    public ResponseEntity<String> get();
}
