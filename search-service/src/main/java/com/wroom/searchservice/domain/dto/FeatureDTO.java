package com.wroom.searchservice.domain.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
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
	private String name;

	private Long brandId;
	
}
