package xwsagent.wroomagent.converter;

import xwsagent.wroomagent.domain.auth.User;
import xwsagent.wroomagent.domain.dto.UserDTO;

public class UserConverter extends AbstractConverter {

	public static UserDTO fromEntity(User entity) {
		return new UserDTO(
				entity.getId(),
				entity.getEmail(),
				entity.getPassword(),
				entity.getName(),
				entity.getSurname(),
				entity.isNonLocked()
		);
	}

	
	
}
