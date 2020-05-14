package xwsagent.wroomagent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import xwsagent.wroomagent.converter.RoleConverter;
import xwsagent.wroomagent.domain.Role;
import xwsagent.wroomagent.domain.User;
import xwsagent.wroomagent.domain.dto.LoggedUserDTO;
import xwsagent.wroomagent.security.TokenUtils;
import xwsagent.wroomagent.security.auth.JwtAuthenticationRequest;

@Service
public class AuthenticationService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
    private TokenUtils tokenUtils;
	
	
	public LoggedUserDTO login(JwtAuthenticationRequest request) {
		final Authentication auth = this.authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		String username = ((User) auth.getPrincipal()).getEmail();
		Role role = (Role)((User) auth.getPrincipal()).getRoles().toArray()[0];
		System.out.println("------>" + " " + username + " IS LOGGED IN!");
		
		if(username == null) {
			return null;
		}
		
		String jwt = tokenUtils.generateToken(username);
//		int expiresIn = tokenUtils.getExpiredIn();
		// TODO: fetch role from db
		
		return new LoggedUserDTO(null, username, RoleConverter.fromEntity(role), jwt);
	}

}
