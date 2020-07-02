package xwsagent.wroomagent.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xwsagent.wroomagent.domain.enums.DebtStatus;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DebtDTO {

	private Long id;
	private Double miles;
	private Long priceListId;
	private Long rentRequestId;
	private DebtStatus status;
}
