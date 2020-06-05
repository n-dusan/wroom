package com.wroom.zuulapigateway.feigns;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "vehicle-service")
public interface VehicleClient {

}
