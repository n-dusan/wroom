package xwsagent.wroomagent.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xwsagent.wroomagent.domain.auth.User;
import xwsagent.wroomagent.domain.enums.RequestStatus;

@Entity
@Getter @Setter @NoArgsConstructor
public class RentRequest {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Enumerated(EnumType.STRING)
	private RequestStatus status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date fromDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date toDate;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private User requestedUser;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Ad> ads;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private RentReport rentReport;

}
