package xwsagent.wroomagent.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xwsagent.wroomagent.domain.auth.SignupRequestDTO;
import xwsagent.wroomagent.exception.UsernameAlreadyExistsException;
import xwsagent.wroomagent.service.AuthenticationService;

@RestController
@RequestMapping(value = "api/auth")
public class AuthController {

	@Autowired
	private AuthenticationService authService;

//	@PostMapping(value = "/login")
//	public ResponseEntity<LoggedUserDTO> login(@RequestBody JwtAuthenticationRequest request) {
//		
//	}
//
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequestDTO signUpRequest) {
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
