package xwsagent.wroomagent.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AdDTO {

	private Long id;
	private String publishDate;
	private String availableFrom;
	private String availableTo;
	private Double mileLimit;
	
}
