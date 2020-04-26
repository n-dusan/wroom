package xwsagent.wroomagent.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PriceList {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	

	@Column(nullable = false)
	private Double pricePerDay;
	
	@Column
	private Double pricePerMile;
	
	@Column
	private Double priceCDW;
	
	@Column
	private Double discount;

	@OneToMany(mappedBy = "priceList")
	private Set<Ad> ads;

	
}
