package wroom.authservice.dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CompanyDTO {
private Long id;
	
	@NotNull
	@NotEmpty
	private String name;
	
	@NotNull
	@NotEmpty
	@Pattern(regexp ="^((https?|ftp|smtp):\\/\\/)?(www.)?[a-z0-9]+\\.[a-z]+(\\/[a-zA-Z0-9#]+\\/?)*$")
	private String website;
	
	@NotNull
	@NotEmpty
	@Email(regexp = ".+@.+\\..+", message = "Email must be valid.")
	private String email;
	
	private Boolean enabled;
	
	private Boolean nonLocked;
	
	@Size(min=10, max = 10)
	@NotNull
	@NotEmpty
	private String businessNumber;
	
	@NotNull
	@NotEmpty
	private String address;
	
}
