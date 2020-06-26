package xwsagent.wroomagent.service;

import org.springframework.stereotype.Service;

import xwsagent.wroomagent.converter.UserConverter;
import xwsagent.wroomagent.domain.auth.Role;
import xwsagent.wroomagent.domain.auth.RoleName;
import xwsagent.wroomagent.domain.auth.User;
import xwsagent.wroomagent.domain.dto.RoleDTO;
import xwsagent.wroomagent.domain.dto.UserDTO;
import xwsagent.wroomagent.exception.InvalidReferenceException;
import xwsagent.wroomagent.repository.rbac.RoleRepository;
import xwsagent.wroomagent.repository.rbac.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

	public UserService(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	public List<User> findAllEnabled() {
		List<User> list = this.userRepository.findAllEnabled();
		List<User> ret = new ArrayList<User>();
		for(User u : list) {
			if(!u.getRoles().contains(roleRepository.findByName(RoleName.ROLE_ADMIN))) {
				ret.add(u);
			}
		}
		return ret;
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
	
	public void addPermissions(Long id, List<String> roles) {
		User user = findById(id);
		Set<Role>list = user.getRoles();
		
		if(roles.contains("ROLE_CRUD_VEHICLE")) {
			list.add(roleRepository.findByName(RoleName.ROLE_CRUD_VEHICLE));
						
		}else {
			list.remove(roleRepository.findByName(RoleName.ROLE_CRUD_VEHICLE));
	    }
		
		if(roles.contains("ROLE_CRUD_AD")) {
			list.add(roleRepository.findByName(RoleName.ROLE_CRUD_AD));
						
		}else {
			list.remove(roleRepository.findByName(RoleName.ROLE_CRUD_AD));
	    }
		
		if(roles.contains("ROLE_PHYSICALLY_RESERVE")) {
			list.add(roleRepository.findByName(RoleName.ROLE_PHYSICALLY_RESERVE));
						
		}else {
			list.remove(roleRepository.findByName(RoleName.ROLE_PHYSICALLY_RESERVE));
	    }
		
		if(roles.contains("ROLE_RENTING_USER")) {
			list.add(roleRepository.findByName(RoleName.ROLE_RENTING_USER));
						
		}else {
			list.remove(roleRepository.findByName(RoleName.ROLE_RENTING_USER));
	    }
		
		if(roles.contains("ROLE_CHATTING_USER")) {
			list.add(roleRepository.findByName(RoleName.ROLE_CHATTING_USER));
						
		}else {
			list.remove(roleRepository.findByName(RoleName.ROLE_CHATTING_USER));
	    }
		
		if(roles.contains("ROLE_RATING_COMMENTING_USER")) {
			list.add(roleRepository.findByName(RoleName.ROLE_RATING_COMMENTING_USER));
						
		}else {
			list.remove(roleRepository.findByName(RoleName.ROLE_RATING_COMMENTING_USER));
	    }
		
		userRepository.save(user);
	}
}
