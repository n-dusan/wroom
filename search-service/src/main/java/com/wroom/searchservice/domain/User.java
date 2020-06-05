package com.wroom.searchservice.domain;

import java.util.Set;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Cacheable(false)
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

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "requestedUser")
	private Set<RentRequest> rentRequests;

//	@OneToMany(mappedBy = "owner")
//	private Set<Vehicle> vehicles;

	@OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Comment> comments;

	@OneToMany(mappedBy = "client")
	private Set<Rate> rates;

	@Column(nullable = false)
	private boolean enabled;

	@Column(nullable = false)
	private boolean nonLocked;

}
