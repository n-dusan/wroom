package xwsagent.wroomagent.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignupRequestDTO {

	private String email;
	private String password;
	private String name;
	private String surname;
	
}
