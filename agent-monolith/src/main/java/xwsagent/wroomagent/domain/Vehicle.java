package xwsagent.wroomagent.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Vehicle {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name = "mileage")
	private Integer mileage;
	
	@Column(name = "childseats")
	private Integer childSeats;
	
	@Column(name = "cdw")
	private boolean cdw;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private VehicleBrand vehicleBrand;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private VehicleModel vehicleModel;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private FuelType fuelType;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private BodyType bodyType;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private GearboxType gearboxType;

}
