package com.wroom.rentingservice.domain;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter @Setter @NoArgsConstructor
@ToString
public class Ad {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date publishDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date availableFrom;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date availableTo;
	
	@Column
	private Double mileLimit;

	@Column
	private boolean mileLimitEnabled;

	@Column
	private boolean gps;

	@Column
	private boolean deleted;

	@Column
	private String address;
	
	@Column
	private Long vehicleId;

	@Column
	private Long priceListId;

	@Column
	private Long locationId;

	@Column
	private Long localId;

//	@OneToMany(mappedBy = "ad")
//	private Set<Rate> rates;
}
