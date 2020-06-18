package xwsagent.wroomagent.domain.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xwsagent.wroomagent.domain.enums.RequestStatus;

import javax.validation.constraints.NotNull;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RentRequestDTO {

	private Long id;
	private RequestStatus status;

	@NotNull(message = "from can't be null")
	private Date fromDate;

	@NotNull(message = "to can't be null")
	private Date toDate;


	private Long requestedUserId;
	private AdDTO ad;
	private Long bundleId;
	private Long reportId;
}
