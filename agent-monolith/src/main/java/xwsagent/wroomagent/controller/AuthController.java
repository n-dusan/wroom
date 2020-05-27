package xwsagent.wroomagent.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;
import xwsagent.wroomagent.config.EndpointConfig;
import xwsagent.wroomagent.domain.dto.LoggedUserDTO;
import xwsagent.wroomagent.domain.dto.LoginRequestDTO;
import xwsagent.wroomagent.domain.dto.SignupRequestDTO;
import xwsagent.wroomagent.exception.APIError;
import xwsagent.wroomagent.exception.UsernameAlreadyExistsException;
import xwsagent.wroomagent.service.AuthenticationService;
import xwsagent.wroomagent.util.RequestCounter;

@RestController
@RequestMapping(value = EndpointConfig.AUTH_BASE_URL)
@Log4j2
public class AuthController {

	private static final String LOG_LOGIN = "action=login user=%s ip_address=%s times=%s ";
	private static final String LOG_SIGN_UP= "action=signup user=%s ip_address=%s times=%s ";

	private final AuthenticationService authService;
	private final RequestCounter requestCounter;

	public AuthController(AuthenticationService authenticationService, RequestCounter requestCounter) {
		this.authService = authenticationService;
		this.requestCounter = requestCounter;
	}


	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO request, HttpServletRequest httpServletRequest) {

		String logContent = String.format(LOG_LOGIN, request.getEmail(), httpServletRequest.getRemoteAddr(), requestCounter.get(EndpointConfig.AUTH_BASE_URL));
		try {
			log.info(logContent);
			LoggedUserDTO ret = this.authService.login(request);
			return new ResponseEntity<>(ret, HttpStatus.OK);
		} catch(BadCredentialsException ex) {
			log.error(logContent + "Bad credentials");
			List<String> errors = new ArrayList<String>();
			errors.add("Bad Credentials");
			return new ResponseEntity<>(new APIError(HttpStatus.BAD_REQUEST, "Bad Credentials", errors), HttpStatus.BAD_REQUEST);
		} catch(Exception e) {
			log.error(logContent + "General exception");
			e.printStackTrace();
		}

		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequestDTO signUpRequest, HttpServletRequest httpServletRequest) {
		String logContent = String.format(LOG_SIGN_UP, signUpRequest.getEmail(), httpServletRequest.getRemoteAddr(), requestCounter.get(EndpointConfig.AUTH_BASE_URL));
		try {
			if (this.authService.signup(signUpRequest)) {
				log.info(logContent);
				return new ResponseEntity<>(HttpStatus.CREATED);
			}
			log.error(logContent);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (UsernameAlreadyExistsException e) {
			log.error(logContent + "Username already exists");
			return new ResponseEntity<>(HttpStatus.IM_USED);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@GetMapping("/whoami")
	public ResponseEntity<?> whoami(Authentication auth) {
		return new ResponseEntity<>(this.authService.whoami(auth), HttpStatus.OK);
	}
	
 
	/*
	 * Endpoint for e-mail confirmation
	 */
	@PutMapping("/confirm")
	public ResponseEntity<?> emailConfirmation(@RequestBody String token) {
		return new ResponseEntity<>(this.authService.confirm(token), HttpStatus.OK);
	}

}
