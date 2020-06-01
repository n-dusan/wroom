package wroom.authservice.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UserDTO {

	private Long id;
	private String name;
	private String surname;
	private String email;
	private Set<String> privileges;
	private Boolean nonLocked;
	
}
