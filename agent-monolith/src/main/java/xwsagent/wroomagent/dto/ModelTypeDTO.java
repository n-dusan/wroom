package xwsagent.wroomagent.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xwsagent.wroomagent.domain.ModelType;

@Getter
@Setter
@NoArgsConstructor
public class ModelTypeDTO {

	private Long id;
	private String name;
	
	public ModelTypeDTO(ModelType modelType) {
		this(modelType.getId(), modelType.getName());
	}

	public ModelTypeDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}
}
