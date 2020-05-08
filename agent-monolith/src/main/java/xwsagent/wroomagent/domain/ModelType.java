package xwsagent.wroomagent.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xwsagent.wroomagent.domain.dto.ModelTypeDTO;

@Entity
@Getter @Setter @NoArgsConstructor
public class ModelType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String name;
	
	@Column(name = "deleted")
	private boolean deleted;

	public ModelType(ModelTypeDTO dto) {
		this.name = dto.getName();
		this.deleted = false;
	}
	
}
