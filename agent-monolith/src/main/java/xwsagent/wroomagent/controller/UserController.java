package xwsagent.wroomagent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xwsagent.wroomagent.domain.dto.UserDTO;
import xwsagent.wroomagent.service.UserService;

@RestController
@RequestMapping(value="api/user")
public class UserController {

	@Autowired
	private UserService userService;
	
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
	
	/*
	 * Endpoint for e-mail confirmation
	 */
	@PutMapping("/enable/{token}")
	public ResponseEntity<?> enable(@PathVariable String token) {
        try {
//        	UserDTO user = this.userService.enable(token);
//        	return new ResponseEntity<>(user, HttpStatus.OK);
        } catch(Exception e) {
        	e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
