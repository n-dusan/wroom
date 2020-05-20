package com.wroom.zuulapigateway.feigns;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.wroom.zuulapigateway.dto.SignupRequestDTO;

@FeignClient(name = "auth-service")
public interface AuthClient {

	@GetMapping("/hello")
    public ResponseEntity<String> get();

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequestDTO signUpRequest);
}
