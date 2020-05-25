package xwsagent.wroomagent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xwsagent.wroomagent.config.EndpointConfig;
import xwsagent.wroomagent.domain.dto.UserDTO;
import xwsagent.wroomagent.service.UserService;

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
	
}
