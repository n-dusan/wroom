package com.wroom.zuulapigateway.feigns;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "renting-service")
public interface RentingClient {

}
