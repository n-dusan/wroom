package xwsagent.wroomagent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xwsagent.wroomagent.converter.UserConverter;
import xwsagent.wroomagent.domain.auth.User;
import xwsagent.wroomagent.domain.dto.UserDTO;
import xwsagent.wroomagent.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User findByEmail(String email) {
		return this.userRepository.findByEmail(email).get();
	}
	
	public UserDTO activate(Long id) {
		User usr = userRepository.getOne(id);
		usr.setEnabled(true);
		userRepository.saveAndFlush(usr);
		
		return UserConverter.fromEntity(usr);
	}
}
