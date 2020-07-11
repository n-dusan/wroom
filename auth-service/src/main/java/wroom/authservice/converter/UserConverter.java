package wroom.authservice.converter;

import java.util.HashSet;
import java.util.Set;

import wroom.authservice.domain.Role;
import wroom.authservice.domain.User;
import wroom.authservice.dto.CompanyDTO;
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
	
	public static User toEntity(CompanyDTO dto) {
		User user = new User();

		user.setName(dto.getName());
		user.setSurname(dto.getWebsite());
		user.setEmail(dto.getEmail());
		user.setEnabled(dto.getEnabled());
		user.setNonLocked(dto.getNonLocked());
		user.setBusinessNumber(dto.getBusinessNumber());
		user.setAddress(dto.getAddress());

		return user;
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
