package xwsagent.wroomagent.domain.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FeatureDTO {

	private Long id;
	
	@Size(max = 20, message="must be less than or equal to 20")
	@NotBlank(message = "Vehicle feature cannot have an empty name!")
	@Pattern(regexp = "^[A-Za-z0-9]+$", message = "Only alphanumeric characters")
	private String name;

	private Long brandId;
	
}
