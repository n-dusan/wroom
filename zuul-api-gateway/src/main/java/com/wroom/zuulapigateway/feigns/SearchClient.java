package com.wroom.zuulapigateway.feigns;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "search-service")
public interface SearchClient {
}
