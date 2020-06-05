package com.wroom.searchservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wroom.searchservice.converter.UserConverter;
import com.wroom.searchservice.domain.User;
import com.wroom.searchservice.domain.dto.UserDTO;
import com.wroom.searchservice.exceptions.InvalidReferenceException;
import com.wroom.searchservice.repository.UserRepository;

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


	public User registerAccount(UserDTO userDTO) {
		User user = new User();

		user.setId(userDTO.getId());
		user.setNonLocked(userDTO.getNonLocked());
		user.setEnabled(userDTO.getEnabled());
		user.setEmail(userDTO.getEmail());
		user.setName(userDTO.getName());
		user.setSurname(userDTO.getSurname());

		return userRepository.save(user);
	}
}
