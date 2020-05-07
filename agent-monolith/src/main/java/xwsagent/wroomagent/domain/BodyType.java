package xwsagent.wroomagent.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Set;


@Entity
@Getter @Setter @NoArgsConstructor
public class BodyType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	

	@Column(nullable = false)
	private String name;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "bodyType")
	private Set<Vehicle> vehicles;
	
	@Column(name = "deleted")
	private boolean deleted;
	
	public BodyType(Long id, String name, boolean deleted) {
		super();
		this.id = id;
		this.name = name;
		this.deleted = false;
	}

}
