package com.wroom.rentingservice.domain;

import java.util.Set;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class BundledRequests {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@OneToMany(mappedBy = "bundle", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<RentRequest> requests;

	@Column
	private Long localId;
	
}
