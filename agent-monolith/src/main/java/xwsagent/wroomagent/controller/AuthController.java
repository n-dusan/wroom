package xwsagent.wroomagent.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import xwsagent.wroomagent.domain.ErrorResponse;
import xwsagent.wroomagent.domain.dto.LoggedUserDTO;
import xwsagent.wroomagent.domain.dto.LoginRequestDTO;
import xwsagent.wroomagent.domain.dto.SignupRequestDTO;
import xwsagent.wroomagent.exception.UsernameAlreadyExistsException;
import xwsagent.wroomagent.service.AuthenticationService;

@RestController
@RequestMapping(value = "api/auth")
public class AuthController {

	@Autowired
	private AuthenticationService authService;

	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
		try {
			
			LoggedUserDTO ret = this.authService.login(request);
			return new ResponseEntity<>(ret, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequestDTO signUpRequest) {
		try {
			if (this.authService.signup(signUpRequest)) {
				return new ResponseEntity<>(HttpStatus.CREATED);
			}
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (UsernameAlreadyExistsException e) {
			return new ResponseEntity<>(HttpStatus.IM_USED);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        String s = ex.getLocalizedMessage();
        ErrorResponse error = new ErrorResponse(s, details);
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
 
}
