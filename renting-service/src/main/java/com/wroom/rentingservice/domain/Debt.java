package com.wroom.rentingservice.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.wroom.rentingservice.domain.enums.DebtStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Debt {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private Double miles;
	
	@Column
	private Long priceListId;
	
	@Column
	private Long rentRequestId;
	
	//@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    //private User user;
	
	@Column
	private DebtStatus status;
}