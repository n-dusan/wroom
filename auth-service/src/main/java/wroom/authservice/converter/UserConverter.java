package wroom.authservice.converter;

import java.util.HashSet;
import java.util.Set;

import wroom.authservice.domain.Role;
import wroom.authservice.domain.User;
import wroom.authservice.dto.UserDTO;

public class UserConverter extends AbstractConverter{

	public static UserDTO fromEntity(User entity) {
		return new UserDTO(
				entity.getId(),
				entity.getName(),
				entity.getSurname(),
				entity.getEmail(),
				getRoles(entity.getRoles()),
				entity.isNonLocked(),
				entity.isEnabled()
		);
	}
	
	public static Set<String> getRoles(Set<Role> roles) {
		Set<String> ret = new HashSet<String>();
//		roles.forEach(x -> ret.add(x.getName().toString()));
		for(Role r : roles) {
			ret.add(r.getName().toString());
		}
		return ret;
	}
	
}
