package com.wroom.searchservice.converter;

import com.wroom.searchservice.domain.User;
import com.wroom.searchservice.domain.dto.UserDTO;

public class UserConverter extends AbstractConverter {

	public static UserDTO fromEntity(User entity) {
		return new UserDTO(
				entity.getId(),
				entity.getEmail(),
				entity.getName(),
				entity.getSurname(),
				entity.isNonLocked(),
				entity.isEnabled()
		);
	}

	
	
}
