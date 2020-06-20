package xwsagent.wroomagent.domain.auth;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xwsagent.wroomagent.domain.Comment;
import xwsagent.wroomagent.domain.RentRequest;
import xwsagent.wroomagent.domain.Vehicle;

@Entity
@Table(name = "users")
@NamedEntityGraph(name = "User.Roles.Permissions", attributeNodes = @NamedAttributeNode(value = "roles", subgraph = "permissions"), subgraphs = @NamedSubgraph(name = "permissions", attributeNodes = @NamedAttributeNode("permissions")))
@Inheritance(strategy = InheritanceType.JOINED)
@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class User {
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

//	@OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	private Set<Comment> comments;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	@Column(nullable = false)
	private boolean enabled;

	@Column(nullable = false)
	private boolean nonLocked;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date lastPasswordChange;
	
	@Column
	private String businessNumber;
	
	@Column
	private String address;

}
