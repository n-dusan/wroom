package com.wroom.zuulapigateway.feigns;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wroom.zuulapigateway.dto.auth.LoginRequestDTO;
import com.wroom.zuulapigateway.dto.auth.SignupRequestDTO;


@FeignClient(name = "auth-service")
public interface AuthClient {

	@GetMapping("auth/hello")
    public ResponseEntity<String> get();

	@PostMapping("auth/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequestDTO signUpRequest);
	
	@PostMapping(value = "auth/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDTO request);
	
	@GetMapping("auth/whoami")
	public ResponseEntity<?> whoami(Authentication auth);
	
	@PutMapping("auth/confirm")
	public ResponseEntity<?> emailConfirmation(@RequestBody String token);

	@PutMapping(value="user/activate/{id}")
	public ResponseEntity<?> activate(@PathVariable Long id);

	@GetMapping(value="user")
	public ResponseEntity<?> getUsers();

	@PutMapping(value="user/lock/{id}")
	public ResponseEntity<?> lockUser(@PathVariable("id") Long id);

	@PutMapping(value="user/unlock/{id}")
	public ResponseEntity<?> unlockUser(@PathVariable("id") Long id);

	@DeleteMapping(value = "user/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> delete(@PathVariable("id") Long id);

}
