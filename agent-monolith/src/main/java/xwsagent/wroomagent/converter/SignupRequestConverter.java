package xwsagent.wroomagent.converter;

import xwsagent.wroomagent.domain.SignupRequest;
import xwsagent.wroomagent.dto.SignupRequestDTO;

public class SignupRequestConverter extends AbstractConverter {

	//TODO: Hash password 
	public static SignupRequestDTO fromEntity(SignupRequest entity) {
		return new SignupRequestDTO(
				entity.getEmail(),
				entity.getPassword(),
				entity.getName(),
				entity.getSurname()
		);
	}
	
	public static SignupRequest toEntity(SignupRequestDTO dto) {
		SignupRequest entity = new SignupRequest();
		entity.setEmail(dto.getEmail());
		entity.setPassword(dto.getPassword());
		entity.setName(dto.getName());
		entity.setSurname(dto.getSurname());
		
		return entity;
	}
}
