package wroom.authservice.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import wroom.authservice.converter.UserConverter;
import wroom.authservice.domain.Role;
import wroom.authservice.domain.RoleName;
import wroom.authservice.domain.User;
import wroom.authservice.domain.VerificationToken;
import wroom.authservice.dto.LoggedUserDTO;
import wroom.authservice.dto.LoginRequestDTO;
import wroom.authservice.dto.SignupRequestDTO;
import wroom.authservice.dto.UserDTO;
import wroom.authservice.exception.UsernameAlreadyExistsException;
import wroom.authservice.jwt.JwtTokenProvider;
import wroom.authservice.jwt.UserPrincipal;
import wroom.authservice.producer.MailProducer;
import wroom.authservice.repository.RoleRepository;
import wroom.authservice.repository.UserRepository;
import wroom.authservice.repository.VerificationTokenRepository;

@Service
public class AuthenticationService {

	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder encoder;
	private final JwtTokenProvider jwtProvider;
    private final MailProducer mailProducer;
	private final VerificationTokenRepository verificationRepository;

	public AuthenticationService(AuthenticationManager authenticationManager,
								 UserRepository userRepository,
								 RoleRepository roleRepository,
								 PasswordEncoder passwordEncoder,
								 JwtTokenProvider jwtProvider,
								 MailProducer mailProducer,
								 VerificationTokenRepository verificationRepository) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.encoder = passwordEncoder;
		this.jwtProvider = jwtProvider;
		this.mailProducer = mailProducer;
		this.verificationRepository = verificationRepository;
	}


	public LoggedUserDTO login(LoginRequestDTO request) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getEmail(),
				request.getPassword());
		Authentication authentication = authenticationManager.authenticate(token);
 
		String email = authentication.getName();
		List<String> authorities = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		String jwt = jwtProvider.generateToken(authentication);
		UserPrincipal user = (UserPrincipal)authentication.getPrincipal();
		System.out.println("My authorities");
		for (String authority : authorities) {
			System.out.println("Auth" +  authority);
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return new LoggedUserDTO(user.getId(), email, authorities, jwt);
	}

	public boolean signup(SignupRequestDTO request) throws UsernameAlreadyExistsException {
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new UsernameAlreadyExistsException("Username already exists!");
		}

		Set<Role> roles = new HashSet<Role>();
		roles.add(this.roleRepository.findByName(RoleName.ROLE_USER));
		roles.add(this.roleRepository.findByName(RoleName.ROLE_RENTING_USER));
		User user = new User(null, request.getName(), request.getSurname(), request.getEmail(),
				encoder.encode(request.getPassword()),
				roles, false, true);

		user.setEnabled(true);
		user.setNonLocked(false);

		userRepository.save(user);

		String token = this.createVerificationToken(user);
		//TODO: poseban servis za mejl slanja
		//mailProducer.sendConfirmationMail(user.getEmail(), token);

		return true;
	}

	private String createVerificationToken(User user) {
		String token = UUID.randomUUID().toString();
		VerificationToken myToken = new VerificationToken(null,token,user,calculateExpiryDate());
		return verificationRepository.save(myToken).getToken();
	}

	private Date calculateExpiryDate() {
		int aDay = 1440;
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Timestamp(cal.getTime().getTime()));
		cal.add(Calendar.MINUTE, aDay);
		return new Date(cal.getTime().getTime());
	}

	public LoggedUserDTO whoami(Authentication auth) {
		UserPrincipal user = (UserPrincipal) auth.getPrincipal();
		List<String> authorities = auth.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		String jwt = jwtProvider.generateToken(auth);
		return new LoggedUserDTO(user.getId(), user.getUsername(), authorities, jwt);
	}

	public UserDTO confirm(String token) {
		VerificationToken vt = this.verificationRepository.findByToken(token);
		User user = vt.getUser();
		user.setEnabled(true);
		this.userRepository.saveAndFlush(user);
		return UserConverter.fromEntity(user);
	}
	
}
