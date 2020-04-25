package xwsagent.wroomagent.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PriceList {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "priceperday")
	private Double pricePerDay;
	
	@Column(name = "pricepermile")
	private Double pricaPerMile;
	
	@Column(name = "pricecdw")
	private Double priceCDW;
	
	@Column(name = "discount")
	private Double discount;

	
}
