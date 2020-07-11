package xwsagent.wroomagent.converter;

import xwsagent.wroomagent.domain.Debt;
import xwsagent.wroomagent.domain.dto.DebtDTO;

public class DebtConverter extends AbstractConverter{

	public static DebtDTO fromEntity(Debt debt) {
		return new DebtDTO(
				debt.getId(),
				debt.getMiles(),
				debt.getPriceListId(),
				debt.getRentRequestId(),
				debt.getStatus()
		);
	}
	
	public static Debt toEntity(DebtDTO dto) {
		Debt debt = new Debt();
		debt.setId(dto.getId());
		debt.setMiles(dto.getMiles());
		debt.setPriceListId(dto.getPriceListId());
		debt.setRentRequestId(dto.getRentRequestId());
		return debt;
	}
}
