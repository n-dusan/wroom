package xwsagent.wroomagent.domain.dto;

import javax.validation.constraints.Max;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeatureDTO {

	private Long id;
	
	@Max(5)

	@NotBlank(message = "Vehicle feature cannot have an empty name!")
	private String name;


	
}
