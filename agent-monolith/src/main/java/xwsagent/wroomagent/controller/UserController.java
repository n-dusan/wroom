package xwsagent.wroomagent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xwsagent.wroomagent.domain.dto.UserDTO;
import xwsagent.wroomagent.dto.SignupRequestDTO;
import xwsagent.wroomagent.service.UserService;

@RestController
@RequestMapping(value="api/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping(value = "/request/approve/{id}")
	public ResponseEntity<UserDTO> approveRequest(@PathVariable Long id) {
		UserDTO ret = this.userService.approveRequest(id);
		
		if(ret == null) {
			return new ResponseEntity<>(ret, HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(ret, HttpStatus.CREATED);
	}
	
}
