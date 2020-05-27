package xwsagent.wroomagent.domain;


import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter @Setter @NoArgsConstructor
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
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Vehicle vehicle;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private PriceList priceList;

	@OneToMany(mappedBy = "ad")
	private Set<Rate> rates;


	@OneToMany(mappedBy="vehicle")
	private Set<Image> image;
}
