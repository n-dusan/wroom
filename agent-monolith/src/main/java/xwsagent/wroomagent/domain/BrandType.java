package xwsagent.wroomagent.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class BrandType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String name;

	@OneToMany(mappedBy = "brandType")
	private Set<ModelType> models;
	
	@Column(name = "deleted")
	private boolean deleted;

	public BrandType(Long id, String name, boolean deleted) {
		super();
		this.id = id;
		this.name = name;
		this.deleted = false;
	}

	public BrandType(String name) {
		this.name = name;
		this.deleted = false;
	}

	
}
