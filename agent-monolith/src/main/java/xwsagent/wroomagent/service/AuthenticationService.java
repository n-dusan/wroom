package xwsagent.wroomagent.service;

import java.util.Collections;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import xwsagent.wroomagent.domain.Comment;
import xwsagent.wroomagent.domain.RentRequest;
import xwsagent.wroomagent.domain.auth.RoleName;
import xwsagent.wroomagent.domain.auth.SignupRequestDTO;
import xwsagent.wroomagent.domain.auth.User;
import xwsagent.wroomagent.exception.UsernameAlreadyExistsException;
import xwsagent.wroomagent.repository.RoleRepository;
import xwsagent.wroomagent.repository.UserRepository;

@Service
public class AuthenticationService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
    PasswordEncoder encoder;

//	
//	public LoggedUserDTO login(LoginRequest request) {
//		
//	}
//	

	public boolean signup(SignupRequestDTO request) throws UsernameAlreadyExistsException {
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new UsernameAlreadyExistsException("Username already exists!");
		}

		User user = new User(null, 
							request.getName(), 
							request.getSurname(), 
							request.getEmail(), 
							encoder.encode(request.getPassword()), 
							new HashSet<RentRequest>(), 
							null, new HashSet<Comment>(), 
							null, 
							Collections.singleton(roleRepository.findByName(RoleName.ROLE_USER)), 
							false, 
							true);
		
		user.setEnabled(false);

		userRepository.save(user);
		return true;
	}

	
	
}
