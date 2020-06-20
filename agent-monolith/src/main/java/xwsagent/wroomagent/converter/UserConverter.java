package xwsagent.wroomagent.converter;

import xwsagent.wroomagent.domain.auth.User;
import xwsagent.wroomagent.domain.dto.CompanyDTO;
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

	public static CompanyDTO fromEntityCompany(User entity) {
		return new CompanyDTO(
				entity.getId(),
				entity.getName(),
				entity.getSurname(),
				entity.getEmail(),
				entity.isEnabled(),
				entity.isNonLocked(),
				entity.getBusinessNumber(),
				entity.getAddress()
		);
	}
	
}
