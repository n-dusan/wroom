package xwsagent.wroomagent.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import xwsagent.wroomagent.config.EndpointConfig;
import xwsagent.wroomagent.converter.UserConverter;
import xwsagent.wroomagent.domain.dto.UserDTO;
import xwsagent.wroomagent.service.UserService;
import xwsagent.wroomagent.util.RequestCounter;

import java.util.List;

@RestController
@RequestMapping(value = EndpointConfig.USER_BASE_URL)
@Log4j2
public class UserController {

	private static final String LOG_ACTIVATE = "action=activate user=%s times=%s details=%s";
	private static final String LOG_LOCK_USER = "action=lock user=%s ip_address=%s times=%s details=%s";
	private static final String LOG_UNLOCK_USER = "action=unlock user=%s ip_address=%s times=%s details=%s";

	private final UserService userService;
	private final RequestCounter requestCounter;

	public UserController(UserService userService, RequestCounter requestCounter) {
		this.userService = userService;
		this.requestCounter = requestCounter;
	}
	
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping(value="/activate/{id}")
	@PreAuthorize("hasAuthority('MANAGE_USERS')")
	public ResponseEntity<UserDTO> activate(@PathVariable Long id, Authentication auth) {
		String logContent = String.format(LOG_ACTIVATE, auth.getName(), requestCounter.get(EndpointConfig.USER_BASE_URL), "Activating user id " + id);
        try {
        	UserDTO user = this.userService.activate(id);
        	log.info(logContent);
        	return new ResponseEntity<>(user, HttpStatus.OK);
        } catch(Exception e) {
        	log.error(logContent);
        	e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

	/**
	 * GET /api/user
	 * @return all enabled (who activated their email) users
	 */
	@GetMapping
	@PreAuthorize("hasAuthority('MANAGE_USERS')")
	public ResponseEntity<List<UserDTO>> getUsers() {
		return new ResponseEntity<>(UserConverter.fromEntityList(this.userService.findAllEnabled(), UserConverter::fromEntity),
				HttpStatus.OK);
	}

	/**
	 * GET /api/user
	 * @return all enabled (who activated their email) users
	 */
	@PutMapping("/lock/{id}")
	@PreAuthorize("hasAuthority('MANAGE_USERS')")
	public ResponseEntity<UserDTO> lockUser(@PathVariable("id") Long id, Authentication auth) {
		String logContent = String.format(LOG_LOCK_USER, auth.getName(), requestCounter.get(EndpointConfig.USER_BASE_URL), "Locking user id " + id);
		log.info(logContent);
		return new ResponseEntity<>(UserConverter.fromEntity(this.userService.lockAccount(id)),
				HttpStatus.OK);
	}

	/**
	 * PUT /api/user/lock
	 * @param id
	 * @return
	 */
	@PutMapping("/unlock/{id}")
	@PreAuthorize("hasAuthority('MANAGE_USERS')")
	public ResponseEntity<UserDTO> unlockUser(@PathVariable("id") Long id, Authentication auth) {
		String logContent = String.format(LOG_UNLOCK_USER, auth.getName(), requestCounter.get(EndpointConfig.USER_BASE_URL), "Unlocking user id " + id);
		log.info(logContent);
		return new ResponseEntity<>(UserConverter.fromEntity(this.userService.unlockAccount(id)),
				HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
	@PreAuthorize("hasAuthority('MANAGE_USERS')")
	public ResponseEntity<String> delete(@PathVariable("id") Long id) {
		userService.delete(id);
		return new ResponseEntity<>("User successfully deleted!", HttpStatus.OK);
	}
	
}
