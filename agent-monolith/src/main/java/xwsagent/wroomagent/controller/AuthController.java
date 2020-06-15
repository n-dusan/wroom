package xwsagent.wroomagent.controller;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;
import xwsagent.wroomagent.config.EndpointConfig;
import xwsagent.wroomagent.domain.dto.LoggedUserDTO;
import xwsagent.wroomagent.domain.dto.LoginRequestDTO;
import xwsagent.wroomagent.domain.dto.ResetPasswordDTO;
import xwsagent.wroomagent.domain.dto.SignupRequestDTO;
import xwsagent.wroomagent.exception.APIError;
import xwsagent.wroomagent.exception.PasswordTokenAlreadyUsed;
import xwsagent.wroomagent.exception.TokenExpiredException;
import xwsagent.wroomagent.exception.UsernameAlreadyExistsException;
import xwsagent.wroomagent.jwt.UserPrincipal;
import xwsagent.wroomagent.service.AuthenticationService;
import xwsagent.wroomagent.util.RequestCounter;

@RestController
@RequestMapping(value = EndpointConfig.AUTH_BASE_URL)
@Log4j2
public class AuthController {

	private static final String LOG_LOGIN = "action=login user=%s ip_address=%s times=%s ";
	private static final String LOG_SIGN_UP= "action=signup user=%s ip_address=%s times=%s ";
	private static final String LOG_FORGOT_PASSWORD = "action=forgot_password email=%s ip_address=%s times=%s";
	private static final String LOG_RESET_PASSWORD = "action=reset_password token=%s ip_address=%s times=%s";
	
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
			return new ResponseEntity<>(
					new APIError(HttpStatus.BAD_REQUEST, "Bad Credentials", Collections.singletonList("Bad credentials")), HttpStatus.BAD_REQUEST);
		} catch(LockedException e) {
			log.error(logContent + "Account locked");
			return new ResponseEntity<>(
					new APIError(HttpStatus.BAD_REQUEST, "Account locked", Collections.singletonList("Account locked")), HttpStatus.BAD_REQUEST);
		} catch(DisabledException e) {
			log.error(logContent + "Account disabled");
			return new ResponseEntity<>(
					new APIError(HttpStatus.BAD_REQUEST, "Account disabled", Collections.singletonList("Account disabled")), HttpStatus.BAD_REQUEST);
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
		try {
			return new ResponseEntity<>(this.authService.whoami(auth), HttpStatus.OK);
		} catch(NullPointerException e) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	/*
	 * Endpoint for e-mail confirmation
	 */
	@PutMapping("/confirm")
	public ResponseEntity<?> emailConfirmation(@RequestBody String token) {
		return new ResponseEntity<>(this.authService.confirm(token), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/forgot-password/{email}")
	public ResponseEntity<?> forgotPassword(@PathVariable("email") String email, HttpServletRequest httpServletRequest) {
		String logContent = String.format(LOG_FORGOT_PASSWORD, email, httpServletRequest.getRemoteAddr(), requestCounter.get(EndpointConfig.AUTH_BASE_URL));
		log.info(logContent);

		try {
			this.authService.forgotPassword(email);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch(PasswordTokenAlreadyUsed e) {
			return new ResponseEntity<>(HttpStatus.IM_USED);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PutMapping(value = "/reset-password")
	public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordDTO token, HttpServletRequest httpServletRequest) {
		String logContent = String.format(LOG_RESET_PASSWORD, token, httpServletRequest.getRemoteAddr(), requestCounter.get(EndpointConfig.AUTH_BASE_URL));
		log.info(logContent);

		try {
			this.authService.resetPassword(token);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch(TokenExpiredException e) {
			System.out.println("Token expired");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
}
