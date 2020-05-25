package xwsagent.wroomagent.domain.dto;


import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {

	private String email;
	
	@Pattern(regexp ="^[^\\\\\\n\\r'\"\\/]*$", message="You cannot enter \\/\n\r\"'")
	private String password;
	
}
