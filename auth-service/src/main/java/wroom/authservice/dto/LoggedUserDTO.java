package wroom.authservice.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LoggedUserDTO {

	private Long id;
	private String email;
	private List<String> privileges;
	private String token;
	
}
