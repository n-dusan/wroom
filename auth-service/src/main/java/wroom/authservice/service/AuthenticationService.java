package wroom.authservice.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
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
import wroom.authservice.domain.PasswordResetToken;
import wroom.authservice.domain.Role;
import wroom.authservice.domain.RoleName;
import wroom.authservice.domain.User;
import wroom.authservice.domain.VerificationToken;
import wroom.authservice.dto.LoggedUserDTO;
import wroom.authservice.dto.LoginRequestDTO;
import wroom.authservice.dto.ResetPasswordDTO;
import wroom.authservice.dto.SignupRequestDTO;
import wroom.authservice.dto.UserDTO;
import wroom.authservice.exception.PasswordTokenAlreadyUsed;
import wroom.authservice.exception.TokenExpiredException;
import wroom.authservice.exception.UsernameAlreadyExistsException;
import wroom.authservice.jwt.JwtTokenProvider;
import wroom.authservice.jwt.UserPrincipal;
import wroom.authservice.producer.MailProducer;
import wroom.authservice.repository.PasswordResetTokenRepository;
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
	private final PasswordResetTokenRepository passwordResetRepository;

	public AuthenticationService(AuthenticationManager authenticationManager, UserRepository userRepository,
			RoleRepository roleRepository, PasswordEncoder encoder, JwtTokenProvider jwtProvider,
			MailProducer mailProducer, VerificationTokenRepository verificationRepository,
			PasswordResetTokenRepository passwordResetRepository) {
		super();
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.encoder = encoder;
		this.jwtProvider = jwtProvider;
		this.mailProducer = mailProducer;
		this.verificationRepository = verificationRepository;
		this.passwordResetRepository = passwordResetRepository;
	}

	public LoggedUserDTO login(LoginRequestDTO request) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getEmail(),
				request.getPassword());
		Authentication authentication = authenticationManager.authenticate(token);

		String email = authentication.getName();
		List<String> authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		String jwt = jwtProvider.generateToken(authentication);
		UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
		System.out.println("My authorities");
		for (String authority : authorities) {
			System.out.println("Auth" + authority);
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
				encoder.encode(request.getPassword()), roles, false, true, null, null, null);

		user.setEnabled(true);
		user.setNonLocked(false);

		userRepository.save(user);

		String token = this.createVerificationToken(user);
		// TODO: poseban servis za mejl slanja
		mailProducer.sendConfirmationMail(user.getEmail(), token);

		return true;
	}

	private String createVerificationToken(User user) {
		String token = UUID.randomUUID().toString();
		VerificationToken myToken = new VerificationToken(null, token, user, calculateExpiryDate());
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
		List<String> authorities = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority)
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

	public void forgotPassword(String email) {

		User user = this.userRepository.findByEmail(email).get();
		if (user != null) {
			PasswordResetToken t = this.passwordResetRepository.findByEmail(email);
			if (t != null) {
				if (t.isUsed()) {
					throw new PasswordTokenAlreadyUsed("This token is already used");
				}
			}

			PasswordResetToken token = createPasswordResetToken(email);
			this.passwordResetRepository.save(token);

			this.mailProducer.sendForgotPasswordEmail(email, token.getToken().toString());
		}

	}

	private PasswordResetToken createPasswordResetToken(String email) {
		PasswordResetToken token = new PasswordResetToken();
		token.setCreationDate(new Date());
		Calendar c = Calendar.getInstance();
		c.setTime(token.getCreationDate());
		c.add(Calendar.DATE, 1); // Valid for 24 hours
		token.setValidTo(c.getTime());
		token.setToken(UUID.randomUUID().toString());
		token.setUsed(false);
		token.setEmail(email);

		return token;
	}

	public void resetPassword(ResetPasswordDTO token) {
		PasswordResetToken t = this.passwordResetRepository.findByToken(token.getToken());

		if (t.getValidTo().before(new Date())) {
			throw new TokenExpiredException("Token is expired.");
		}
		if (t.isUsed()) {
			throw new PasswordTokenAlreadyUsed("This token is already used");
		}

		User user = this.userRepository.findByEmail(t.getEmail()).get();
		if (user != null) {
			user.setPassword(this.encoder.encode(token.getPassword()));
			user.setLastPasswordChange(new Date());
			this.userRepository.save(user);
		}

		t.setUsed(true);
		this.passwordResetRepository.save(t);
	}
}
