package xwsagent.wroomagent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xwsagent.wroomagent.dto.SignupRequestDTO;
import xwsagent.wroomagent.service.UserService;

@RestController
@RequestMapping(value="api/auth")
public class AuthController {

	@Autowired
	private UserService userService;
	
	@PostMapping(value = "/signup")
	public ResponseEntity<SignupRequestDTO> signup(@RequestBody SignupRequestDTO requestDTO) {
		SignupRequestDTO ret = this.userService.signup(requestDTO);
		
		if(ret == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.CREATED);		
	}
	
	
}
