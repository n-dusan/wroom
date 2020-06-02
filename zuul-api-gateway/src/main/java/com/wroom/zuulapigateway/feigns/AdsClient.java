package com.wroom.zuulapigateway.feigns;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "ads-service")
public interface AdsClient {

}
