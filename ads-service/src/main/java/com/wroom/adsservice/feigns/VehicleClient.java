package com.wroom.adsservice.feigns;

import com.wroom.adsservice.domain.dto.VehicleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "vehicle-service")
public interface VehicleClient {

    @GetMapping("/vehicle/{id}")
    VehicleDTO findVehicleById(@PathVariable("id") Long id);

    @GetMapping("/vehicle")
    List<VehicleDTO> getVehicles(@RequestHeader("Authorization") String jwt);
}
