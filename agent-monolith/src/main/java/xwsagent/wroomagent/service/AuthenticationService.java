package xwsagent.wroomagent.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import xwsagent.wroomagent.domain.Privilege;
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
		
		List<Privilege> privileges = (List<Privilege>) ((User) auth.getPrincipal()).getPrivileges();
		ArrayList<String> privStr = new ArrayList<String>();
		for( Privilege p : privileges ) {
			privStr.add(p.getName());
		}
		
		if(username == null) {
			return null;
		}
		
		System.out.println("------>" + " " + username + " IS LOGGED IN!");
		
		String jwt = tokenUtils.generateToken(username);
//		int expiresIn = tokenUtils.getExpiredIn();
		
		String privs = tokenUtils.getPrivilegesFromToken(jwt);
		System.out.println("PRIVILEGES: " + privs);
		
		return new LoggedUserDTO(null, username, privStr , jwt);
	}

}
