package wroom.authservice.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import wroom.authservice.config.EndpointConfig;
import wroom.authservice.dto.LoggedUserDTO;
import wroom.authservice.dto.LoginRequestDTO;
import wroom.authservice.dto.SignupRequestDTO;
import wroom.authservice.exception.APIError;
import wroom.authservice.exception.UsernameAlreadyExistsException;
import wroom.authservice.jwt.UserPrincipal;
import wroom.authservice.service.AuthenticationService;
import wroom.authservice.util.RequestCounter;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = EndpointConfig.AUTH_BASE_URL)
@Log4j2
public class AuthController {


	private static final String LOG_LOGIN = "action=login user=%s ip_address=%s times=%s ";
	private static final String LOG_SIGN_UP= "action=signup user=%s ip_address=%s times=%s ";
	private static final String LOG_CONFIRM = "action=confirm user=%s ip_address=%s times=%s";

	private final AuthenticationService authenticationService;
	private final RequestCounter requestCounter;

	public AuthController(AuthenticationService authenticationService, RequestCounter requestCounter) {
		this.authenticationService = authenticationService;
		this.requestCounter = requestCounter;
	}

	@GetMapping("/hello")
	public ResponseEntity<?> get() throws UnknownHostException {
		System.out.println("I am reached.");
		String ip = InetAddress.getLocalHost().getHostAddress();
		return new ResponseEntity<>(String.format("Hello from auth service with ip address %s", ip), HttpStatus.OK);
	}



	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO request, HttpServletRequest httpServletRequest) {

		String logContent = String.format(LOG_LOGIN, request.getEmail(), httpServletRequest.getRemoteAddr(), requestCounter.get(EndpointConfig.AUTH_BASE_URL));
		try {
			log.info(logContent);
			LoggedUserDTO ret = this.authenticationService.login(request);
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
			if (this.authenticationService.signup(signUpRequest)) {
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
			return new ResponseEntity<>(this.authenticationService.whoami(auth), HttpStatus.OK);
		} catch(NullPointerException e) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	/*
	 * Endpoint for e-mail confirmation
	 */
	@PutMapping("/confirm")
	public ResponseEntity<?> emailConfirmation(@RequestBody String token, Authentication auth, HttpServletRequest httpServletRequest) {
		UserPrincipal user = (UserPrincipal) auth.getPrincipal();
		String logContent = String.format(LOG_CONFIRM, user.getUsername(), httpServletRequest.getRemoteAddr(), requestCounter.get(EndpointConfig.AUTH_BASE_URL));
		log.info(logContent);

		return new ResponseEntity<>(this.authenticationService.confirm(token), HttpStatus.OK);
	}

}
