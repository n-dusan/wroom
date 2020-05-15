package xwsagent.wroomagent.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import xwsagent.wroomagent.converter.RequestToUserConverter;
import xwsagent.wroomagent.converter.SignupRequestConverter;
import xwsagent.wroomagent.converter.UserConverter;
import xwsagent.wroomagent.domain.Privilege;
import xwsagent.wroomagent.domain.SignupRequest;
import xwsagent.wroomagent.domain.User;
import xwsagent.wroomagent.domain.dto.SignupRequestDTO;
import xwsagent.wroomagent.domain.dto.UserDTO;
import xwsagent.wroomagent.repository.PrivilegeRepository;
import xwsagent.wroomagent.repository.SignupRequestRepository;
import xwsagent.wroomagent.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PrivilegeRepository privilegeRepository;
	
	@Autowired
	private SignupRequestRepository signupRequestRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		return user;
	}
	
	/*
	 * Stores a signup request in DB and returns it.
	 */
	public SignupRequestDTO signup(SignupRequestDTO requestDTO) {
		User user = this.findByEmail(requestDTO.getEmail());
		if(user != null) {
			return null;
		}
		
		requestDTO.setPassword(this.passwordEncoder.encode(requestDTO.getPassword()));
		SignupRequest ret = this.signupRequestRepository.save(SignupRequestConverter.toEntity(requestDTO));
		return SignupRequestConverter.fromEntity(ret);
	}
	
	public User findByEmail(String email) {
		return this.userRepository.findByEmail(email);
	}
	
	/*
	 * Admin approving signup request.
	 * Creates an User from SignupRequest and stores it in DB.
	 */
	public UserDTO approveRequest(Long id) {
		SignupRequest request = this.signupRequestRepository.findById(id).get();
		if(request == null) {
			return null;
		}
		
		request.setApproved(true);
		this.signupRequestRepository.saveAndFlush(request);
		
		User newUser = RequestToUserConverter.fromRequest(request);
		newUser.getPrivileges().addAll(this.getPrivilegesForRole("END_USER"));
		
		this.userRepository.save(newUser);
		
		return UserConverter.fromEntity(newUser);
	}
	
	/*
	 * Attach privileges to user with certain role
	 */
	private ArrayList<Privilege> getPrivilegesForRole(String role) {
		
			ArrayList<Privilege> ret = new ArrayList<Privilege>();
			
			switch(role) {
			
			case "END_USER" : {
				ret.add(this.privilegeRepository.findByName("RENT_REQUESTS_CREATE"));
				break;
			}
			case "AGENT" : {
				
				break;
			}
			case "ADMIN" : {
				
				break;
			}
			
			}
			
			return ret;
		
	}
}
