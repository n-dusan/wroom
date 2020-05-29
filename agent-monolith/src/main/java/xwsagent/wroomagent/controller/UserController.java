package xwsagent.wroomagent.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import xwsagent.wroomagent.config.EndpointConfig;
import xwsagent.wroomagent.converter.UserConverter;
import xwsagent.wroomagent.domain.dto.UserDTO;
import xwsagent.wroomagent.service.UserService;

import java.util.List;

@RestController
@RequestMapping(value = EndpointConfig.USER_BASE_URL)
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping(value="/activate/{id}")
	public ResponseEntity<UserDTO> activate(@PathVariable Long id) {
        try {
        	UserDTO user = this.userService.activate(id);
        	return new ResponseEntity<>(user, HttpStatus.OK);
        } catch(Exception e) {
        	e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

	/**
	 * GET /api/user
	 * @return all enabled (who activated their email) users
	 */
	@GetMapping
	public ResponseEntity<List<UserDTO>> getUsers() {
		return new ResponseEntity<>(UserConverter.fromEntityList(this.userService.findAllEnabled(), UserConverter::fromEntity),
				HttpStatus.OK);
	}

	/**
	 * GET /api/user
	 * @return all enabled (who activated their email) users
	 */
	@PutMapping("/lock/{id}")
	public ResponseEntity<UserDTO> lockUser(@PathVariable("id") Long id) {
		return new ResponseEntity<>(UserConverter.fromEntity(this.userService.lockAccount(id)),
				HttpStatus.OK);
	}

	/**
	 * PUT /api/user/lock
	 * @param id
	 * @return
	 */
	@PutMapping("/unlock/{id}")
	public ResponseEntity<UserDTO> unlockUser(@PathVariable("id") Long id) {
		return new ResponseEntity<>(UserConverter.fromEntity(this.userService.unlockAccount(id)),
				HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> delete(@PathVariable("id") Long id) {
		userService.delete(id);
		return new ResponseEntity<>("User successfully deleted!", HttpStatus.OK);
	}
	
}
