package com.wroom.vehicleservice.feigns;

import com.wroom.vehicleservice.domain.dto.AdDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "ads-service")
public interface AdsClient {

    @GetMapping("/hello")
    ResponseEntity<String> hello(@RequestHeader("Authorization") String jwt);

    @GetMapping("ads/vehicle/{id}")
    List<AdDTO> findByVehicle(@PathVariable("id") Long vehicleId);
}
