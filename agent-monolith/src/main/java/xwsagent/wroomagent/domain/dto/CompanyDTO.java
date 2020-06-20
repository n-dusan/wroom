package xwsagent.wroomagent.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CompanyDTO {
	private Long id;
	private String name;
	private String website;
	private String email;
	private Boolean enabled;
	private Boolean nonLocked;
	private String businessNumber;
	private String address;
}
