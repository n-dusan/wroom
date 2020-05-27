package xwsagent.wroomagent.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeatureDTO {

	private Long id;
	
	@Size(max = 20, message="must be less than or equal to 20")
	@NotBlank(message = "Vehicle feature cannot have an empty name!")
	private String name;


	
}
