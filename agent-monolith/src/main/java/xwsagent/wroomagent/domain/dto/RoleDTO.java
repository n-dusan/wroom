package xwsagent.wroomagent.domain.dto;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RoleDTO {

	private String name;
	private ArrayList<PrivilegeDTO> privileges;
	
}
