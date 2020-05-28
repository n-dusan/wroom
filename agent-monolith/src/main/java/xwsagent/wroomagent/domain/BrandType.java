package xwsagent.wroomagent.domain;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor
public class BrandType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String name;

//	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "brandType")
//	private Set<Vehicle> vehicles;
	
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
