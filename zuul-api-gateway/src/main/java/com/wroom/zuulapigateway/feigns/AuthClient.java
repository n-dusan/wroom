package com.wroom.zuulapigateway.feigns;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "auth-service")
public interface AuthClient {

}
