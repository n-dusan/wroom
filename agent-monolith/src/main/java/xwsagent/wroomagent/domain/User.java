package xwsagent.wroomagent.domain;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class User {
	//needs CRUD details, like deleted and review data

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	

	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String surname;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String password;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "requestedUser")
	private Set<RentRequest> rentRequests;

	@OneToMany(mappedBy = "owner")
	private Set<Vehicle> vehicles;

	@OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Comment> comments;

	@OneToMany(mappedBy = "client")
	private Set<Rate> rates;
	
}
