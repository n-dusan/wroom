package com.wroom.rentingservice.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.wroom.rentingservice.domain.enums.RequestStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ToString
public class RentRequest {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Enumerated(EnumType.STRING)
	private RequestStatus status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date fromDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date toDate;
	
//	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	private User requestedUser;
	
	@Column
	private Long requestedUserId;
	
	@ManyToOne
	private Ad ad;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private RentReport rentReport;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private BundledRequests bundle;

}
