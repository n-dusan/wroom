package xwsagent.wroomagent.domain.dto;





import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDTO {

	@NotNull(message = "Email cannot be null.")
	@NotEmpty(message = "Email cannot be empty.")
	@Email(regexp = ".+@.+\\..+", message = "Email must be valid.")
	private String email;

	@Size(min = 5, max = 15, message = "Number of characters must be greater than 4 and less than 16.")
	@Pattern(regexp = "[^A-Za-z0-9]")
	private String password;
	
	@NotNull(message = "Name cannot be null.")
	@NotEmpty(message = "Name cannot be empty.")
	private String name;
	
	@NotNull(message = "Surname cannot be null.")
	@NotEmpty(message = "Surname cannot be empty.")
	private String surname;
	
	
	
}
