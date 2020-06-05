package com.wroom.rentingservice.domain.dto;

import java.util.Date;

import com.wroom.rentingservice.domain.enums.RequestStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RentRequestDTO {

	private Long id;
	private RequestStatus status;
	private Date fromDate; 
	private Date toDate;
	private Long requestedUserId;
	private AdDTO ad;
}
