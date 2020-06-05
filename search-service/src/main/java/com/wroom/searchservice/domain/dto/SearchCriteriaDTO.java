package com.wroom.searchservice.domain.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SearchCriteriaDTO {

	private Long locationId;
	private Date from;
	private Date to;
	
}
