package xwsagent.wroomagent.domain.dto;

import javax.validation.constraints.Max;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeatureDTO {

	private Long id;
	
	@Max(5)
	private String name;
	
}
