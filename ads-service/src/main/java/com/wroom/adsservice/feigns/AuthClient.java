package com.wroom.adsservice.feigns;

import com.wroom.adsservice.domain.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auth-service")
public interface AuthClient {

    @GetMapping("user/{id}")
    UserDTO findUser(@PathVariable("id") Long id);
}
