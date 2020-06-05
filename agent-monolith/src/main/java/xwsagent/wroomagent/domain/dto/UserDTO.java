package xwsagent.wroomagent.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UserDTO {

	private Long id;
	private String email;
	private String password;
	private String name;
	private String surname;
//	roles..?
	private Boolean nonLocked;
	
}
