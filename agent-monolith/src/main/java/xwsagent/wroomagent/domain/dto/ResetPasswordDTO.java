package xwsagent.wroomagent.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ResetPasswordDTO {

	private String token;

	@Size(min = 10, max = 20, message = "Number of characters must be greater than 10 and less than 21.")
	@Pattern(regexp ="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{10,20}$",
			message="You need to enter at least 10 characters, uppercase, lowercase, and some of the following characters: #$@!%&*?")
	private String password;
	
}
