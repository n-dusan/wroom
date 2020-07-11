package com.wroom.rentingservice.domain.dto;

import com.wroom.rentingservice.domain.enums.DebtStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DebtDTO {

	private Long id;
	private Double miles;
	private Long priceListId;
	private Long rentRequestId;
	private DebtStatus status;
}
