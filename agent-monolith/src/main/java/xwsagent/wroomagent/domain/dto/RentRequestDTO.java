package xwsagent.wroomagent.domain.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xwsagent.wroomagent.domain.enums.RequestStatus;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RentRequestDTO {

	private Long id;
	private RequestStatus status;
	private Date fromDate; 
	private Date toDate;
	private UserDTO requestedUser;
	private AdDTO ad;
}
