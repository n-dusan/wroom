package com.wroom.zuulapigateway.feigns;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.wroom.zuulapigateway.dto.LoginRequestDTO;
import com.wroom.zuulapigateway.dto.SignupRequestDTO;

@FeignClient(name = "auth-service")
public interface AuthClient {

	@GetMapping("/hello")
    public ResponseEntity<String> get();

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequestDTO signUpRequest);
	
	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDTO request);
	
	@GetMapping("/whoami")
	public ResponseEntity<?> whoami(Authentication auth);
	
	@PutMapping("/confirm")
	public ResponseEntity<?> emailConfirmation(@RequestBody String token);
}
