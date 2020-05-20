package wroom.authservice.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import wroom.authservice.dto.LoggedUserDTO;
import wroom.authservice.dto.LoginRequestDTO;
import wroom.authservice.dto.SignupRequestDTO;
import wroom.authservice.exception.UsernameAlreadyExistsException;
import wroom.authservice.service.AuthService;

@RestController
public class AuthController {

	@Autowired
	private AuthService authService;

	@GetMapping("/hello")
    public ResponseEntity<?> get() throws UnknownHostException {
        System.out.println("I am reached.");
        String ip = InetAddress.getLocalHost().getHostAddress();
        return new ResponseEntity<>(String.format("Hello from auth service with ip address %s", ip), HttpStatus.OK);
    }

	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
		try {
			LoggedUserDTO ret = this.authService.login(request);
			return new ResponseEntity<>(ret, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequestDTO signUpRequest) {
		try {
			if (this.authService.signup(signUpRequest)) {
				return new ResponseEntity<>(HttpStatus.CREATED);
			}
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (UsernameAlreadyExistsException e) {
			return new ResponseEntity<>(HttpStatus.IM_USED);
		}
	}

}
