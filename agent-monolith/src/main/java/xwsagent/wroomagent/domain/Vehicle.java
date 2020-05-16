package xwsagent.wroomagent.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xwsagent.wroomagent.domain.auth.User;

import java.util.Set;

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

	@Column(nullable = false)
	private Boolean gps;

	//active true-> vehicle isn't already reserved by hand (ručno)
	@Column(nullable = false)
	private Boolean active;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private User owner;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private BrandType brandType;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private ModelType modelType;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private FuelType fuelType;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private BodyType bodyType;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private GearboxType gearboxType;

	@OneToMany(mappedBy = "vehicle")
	private Set<Image> image;

	@OneToMany(mappedBy = "vehicle")
	private Set<Ad> ads;

}
