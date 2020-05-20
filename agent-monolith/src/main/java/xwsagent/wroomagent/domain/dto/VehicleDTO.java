package xwsagent.wroomagent.domain.dto;

import java.util.ArrayList;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xwsagent.wroomagent.domain.BodyType;
import xwsagent.wroomagent.domain.BrandType;
import xwsagent.wroomagent.domain.FuelType;
import xwsagent.wroomagent.domain.GearboxType;
import xwsagent.wroomagent.domain.Image;
import xwsagent.wroomagent.domain.ModelType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {

	private Long id;
	private Double mileage;
	private Integer childSeats;
	private Boolean cdw;
	private ModelType modelType;
	private BrandType brandType;
	private BodyType bodyType;
	private FuelType fuelType;
	private GearboxType gearboxType;
	
}
