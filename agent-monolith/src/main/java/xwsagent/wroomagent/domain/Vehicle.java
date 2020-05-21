package xwsagent.wroomagent.domain;

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
import xwsagent.wroomagent.domain.auth.User;

@Entity
@Getter @Setter @NoArgsConstructor
public class Vehicle {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	
	@Column(nullable = false)
	private Double mileage;
	
	@Column(nullable = false)
	private Integer childSeats;
	
	@Column(nullable = false)
	private Boolean cdw;

	@Column(/*nullable = false*/)
	private Boolean gps;

	//active true-> vehicle isn't already reserved by hand (ručno)
	@Column(/*nullable = false*/)
	private Boolean active;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private User owner;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private BrandType brandType;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private ModelType modelType;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private FuelType fuelType;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private BodyType bodyType;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private GearboxType gearboxType;

	@OneToMany(mappedBy = "vehicle")
	private Set<Image> images;

	@OneToMany(mappedBy = "vehicle")
	private Set<Ad> ads;

}
