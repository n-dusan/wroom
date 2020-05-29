package xwsagent.wroomagent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xwsagent.wroomagent.converter.UserConverter;
import xwsagent.wroomagent.domain.Ad;
import xwsagent.wroomagent.domain.auth.User;
import xwsagent.wroomagent.domain.dto.UserDTO;
import xwsagent.wroomagent.exception.InvalidReferenceException;
import xwsagent.wroomagent.repository.rbac.UserRepository;

import java.util.List;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<User> findAllEnabled() {
		return this.userRepository.findAllEnabled();
	}

	public User findById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new InvalidReferenceException("Unable to find reference to " + id.toString() + " user"));
	}

	public User findByEmail(String email) {
		return this.userRepository.findByEmail(email).get();
	}

	public UserDTO activate(Long id) {
		User usr = userRepository.getOne(id);
//		usr.setEnabled(true);	//Enable-ovanje se radi kada se odradi confirm emaila
		usr.setNonLocked(true);
		userRepository.saveAndFlush(usr);

		return UserConverter.fromEntity(usr);
	}

//	public UserDTO enable(String token) {
//		User usr = userRepository.getOne(id);
//		usr.setEnabled(true);
//		userRepository.saveAndFlush(usr);
//
//		return UserConverter.fromEntity(usr);
//	}

	public void delete(Long id) {
		User user = findById(id);
		user.setEnabled(false);
		userRepository.save(user);
	}


	public User lockAccount(Long id) {
		User user = findById(id);
		user.setNonLocked(false);
		return userRepository.save(user);
	}

	public User unlockAccount(Long id) {
		User user = findById(id);
		user.setNonLocked(true);
		return userRepository.save(user);
	}
}
