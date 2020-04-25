package xwsagent.wroomagent.domain;

import java.util.Set;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xwsagent.wroomagent.domain.enums.RequestStatus;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class RentRequest {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Enumerated(EnumType.STRING)
	private RequestStatus status;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private User requestedUser;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Ad> ads;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private RentReport rentReport;

}
