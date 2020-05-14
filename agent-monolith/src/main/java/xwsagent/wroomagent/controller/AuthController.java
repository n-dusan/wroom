package xwsagent.wroomagent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xwsagent.wroomagent.domain.dto.LoggedUserDTO;
import xwsagent.wroomagent.domain.dto.SignupRequestDTO;
import xwsagent.wroomagent.security.auth.JwtAuthenticationRequest;
import xwsagent.wroomagent.service.AuthenticationService;
import xwsagent.wroomagent.service.UserService;

@RestController
@RequestMapping(value = "api/auth")
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationService authService;

	@PostMapping(value = "/signup")
	public ResponseEntity<SignupRequestDTO> signup(@RequestBody SignupRequestDTO requestDTO) {
		SignupRequestDTO ret = this.userService.signup(requestDTO);

		if (ret == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PostMapping(value = "/login")
	public ResponseEntity<LoggedUserDTO> login(@RequestBody JwtAuthenticationRequest request) {
		try {
			LoggedUserDTO ret = this.authService.login(request);
			if(ret == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(ret, HttpStatus.ACCEPTED);
		} catch(AuthenticationException exc) {
//			if(never logged in)
			exc.printStackTrace();
		}
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

}
