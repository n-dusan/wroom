package wroom.authservice.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import wroom.authservice.converter.UserConverter;
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
@Getter @Setter @AllArgsConstructor
public class AuthService {

	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder encoder;
	private JwtTokenProvider jwtProvider;
    private MailProducer mailProducer;
	private VerificationTokenRepository verificationRepository;
	
	
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
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return new LoggedUserDTO(user.getId(), email, authorities, jwt);
	}

	public boolean signup(SignupRequestDTO request) throws UsernameAlreadyExistsException {
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new UsernameAlreadyExistsException("Username already exists!");
		}

		User user = new User(null, request.getName(), request.getSurname(), request.getEmail(),
				encoder.encode(request.getPassword()),
				Collections.singleton(roleRepository.findByName(RoleName.ROLE_USER)), false, false);
		
		userRepository.save(user);
		
		String token = this.createVerificationToken(user);
		mailProducer.sendConfirmationMail(user.getEmail(), token);
		
		return true;
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
	
}
