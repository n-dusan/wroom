package xwsagent.wroomagent.domain;

import java.sql.Date;
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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ad {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "publishdate")
	private Date publishDate;
	
	@Column(name = "availablefrom")
	private Date availableFrom;
	
	@Column(name = "availableto")
	private Date availableTo;
	
	@Column(name = "milelimit")
	private Double mileLimit;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Vehicle vehicle;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private PriceList priceList;
	
	@OneToMany
	private Set<Image> image;

	
}
