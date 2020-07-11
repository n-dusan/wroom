package xwsagent.wroomagent.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xwsagent.wroomagent.domain.auth.User;
import xwsagent.wroomagent.domain.enums.DebtStatus;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Debt {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private Double miles;
	
	@Column
	private Long priceListId;
	
	@Column
	private Long rentRequestId;
	
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private User user;
	
	@Column
	private DebtStatus status;
}
