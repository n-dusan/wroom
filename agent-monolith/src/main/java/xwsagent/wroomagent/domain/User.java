package xwsagent.wroomagent.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
	// needs CRUD details, like deleted field and review data

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

	@ManyToMany
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Collection<Role> roles;

	@Column
	private boolean enabled;

//	@Column
//	private boolean locked;
//	
//	@Column
//	private boolean expired;
	
	@Column
	private boolean tokenExpired;

	@Column
	private Timestamp lastPasswordResetDate;
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		List<String> privileges = this.getPrivileges();
		for (String privilege : privileges) {
			authorities.add(new SimpleGrantedAuthority(privilege));
		}
		return authorities;
	}

	/*
	 * RBAC privileges are 'same' as Spring Security authorities
	 */
	private List<String> getPrivileges() {
		List<String> privileges = new ArrayList<>();
		List<Privilege> collection = new ArrayList<>();
		
		for (Role role : roles) {
			collection.addAll(role.getPrivileges());
		}
		for (Privilege item : collection) {
			privileges.add(item.getName());
		}
		return privileges;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	// Indicates whether the user's account has expired. An expired account cannot be authenticated.
	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	// Indicates whether the user is locked or unlocked. A locked user cannot be authenticated.
	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	// Indicates whether the user's credentials (password) has expired. Expired
	// credentials prevent authentication.
	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}
}
