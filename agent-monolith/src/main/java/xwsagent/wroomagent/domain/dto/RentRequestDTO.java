package xwsagent.wroomagent.domain.dto;

import java.util.Date;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xwsagent.wroomagent.domain.Ad;
import xwsagent.wroomagent.domain.auth.User;
import xwsagent.wroomagent.domain.enums.RequestStatus;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RentRequestDTO {

	private Long id;
	private RequestStatus status;
	private Date fromDate; 
	private Date toDate;
	private UserDTO requestedUser;
	private Set<AdDTO>ads;
}
