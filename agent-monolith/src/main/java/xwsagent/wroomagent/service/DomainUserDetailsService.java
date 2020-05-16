package xwsagent.wroomagent.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import xwsagent.wroomagent.domain.Comment;
import xwsagent.wroomagent.domain.RentRequest;
import xwsagent.wroomagent.domain.auth.Permission;
import xwsagent.wroomagent.domain.auth.Role;
import xwsagent.wroomagent.domain.auth.RoleName;
import xwsagent.wroomagent.domain.auth.SignupRequestDTO;
import xwsagent.wroomagent.domain.auth.User;
import xwsagent.wroomagent.jwt.UserPrincipal;
import xwsagent.wroomagent.repository.RoleRepository;
import xwsagent.wroomagent.repository.UserRepository;

@Service
public class DomainUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println(email);
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

		return getUserPrincipal(user);
	}

	public void changePassword(User user, String newPassword) {
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email).orElse(null);
	}

	public void changeUserNonLockStatusTrue(User user) {
		user.setNonLocked(true);
		userRepository.save(user);
	}

	public void changeUserNonLockStatusFalse(User user) {
		user.setNonLocked(false);
		userRepository.save(user);
	}

	public List<User> findAllUsers() {
		return userRepository.findUsersExceptSelf();
	}

	public User findCurrentUser() {
		return userRepository.findCurrentUser();
	}

	@Transactional
	public User registerUser(SignupRequestDTO signUpRequest) {
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return null;
		}
		User user = new User(null, signUpRequest.getName(), signUpRequest.getSurname(), signUpRequest.getEmail(),
				signUpRequest.getPassword(), new HashSet<RentRequest>(), null, new HashSet<Comment>(), null,
				Collections.singleton(roleRepository.findByName(RoleName.ROLE_USER)), true, true);
		userRepository.save(user);

		return user;
	}

	private UserPrincipal getUserPrincipal(User user) {
		Stream<String> roles = user.getRoles().stream()
				.map(Role::getName)
				.map(Enum::name);

		Stream<String> permissions = user.getRoles().stream()
				.map(Role::getPermissions)
				.flatMap(Collection::stream)
				.map(Permission::getName);

		List<GrantedAuthority> authorities = Stream.concat(roles, permissions)
				.distinct()
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());

		return new UserPrincipal(user.getId(), user.getPassword(), user.getEmail(), user.isEnabled(), authorities,
				user.isNonLocked());
	}

	public void activateUser(User user) {
		user.setEnabled(true);
		userRepository.save(user);
	}

	public List<String> getUserAuthorities(User user) {
		Stream<String> roles = user.getRoles().stream()
				.map(Role::getName)
				.map(Enum::name);

		Stream<String> permissions = user.getRoles().stream()
				.map(Role::getPermissions)
				.flatMap(Collection::stream)
				.map(Permission::getName);

		List<GrantedAuthority> authorities = Stream.concat(roles, permissions)
				.distinct()
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());

		List<String> auts = new ArrayList<String>();
		for (GrantedAuthority nesto : authorities) {
			auts.add(nesto.getAuthority());
		}

		return auts;
	}
}
