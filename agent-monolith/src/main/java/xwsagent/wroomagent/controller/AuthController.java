package xwsagent.wroomagent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xwsagent.wroomagent.domain.dto.LoggedUserDTO;
import xwsagent.wroomagent.domain.dto.LoginRequestDTO;
import xwsagent.wroomagent.domain.dto.SignupRequestDTO;
import xwsagent.wroomagent.exception.UsernameAlreadyExistsException;
import xwsagent.wroomagent.service.AuthenticationService;

@RestController
@RequestMapping(value = "api/auth")
public class AuthController {

	@Autowired
	private AuthenticationService authService;

	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
		try {
			LoggedUserDTO ret = this.authService.login(request);
			return new ResponseEntity<>(ret, HttpStatus.OK);
		} catch(BadCredentialsException ex) {
			return new ResponseEntity<>("Bad credentials",HttpStatus.BAD_REQUEST);
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
	
	
	@GetMapping("/whoami")
	public ResponseEntity<?> whoami(Authentication auth) {
		return new ResponseEntity<>(this.authService.whoami(auth), HttpStatus.OK);
	}
	

}
