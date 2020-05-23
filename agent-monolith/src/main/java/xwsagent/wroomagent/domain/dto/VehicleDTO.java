package xwsagent.wroomagent.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xwsagent.wroomagent.domain.BodyType;
import xwsagent.wroomagent.domain.BrandType;
import xwsagent.wroomagent.domain.FuelType;
import xwsagent.wroomagent.domain.GearboxType;
import xwsagent.wroomagent.domain.ModelType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {

	private Long id;
	
	@Pattern(regexp = "[0-9]+")
	private Double mileage;
	
	@Size(min = 3, max = 5, message = "The number of seats can be between 3 and 5.")
	@Pattern(regexp = "[0-9]+")
	private Integer childSeats;
	
	private Boolean cdw;
	
	@NotBlank(message = "Model Type may not be blank.")
	private ModelType modelType;
	
	@NotBlank(message = "Brand Type may not be blank.")
	private BrandType brandType;
	
	@NotBlank(message = "Body Type may not be blank.")
	private BodyType bodyType;
	
	@NotBlank(message = "Fuel Type may not be blank.")
	private FuelType fuelType;
	
	@NotBlank(message = "Gearbox Type may not be blank.")
	private GearboxType gearboxType;
	
}
