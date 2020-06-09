package xwsagent.wroomagent.domain;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private UUID token;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date creationDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date validTo;

	@Column(nullable = false)
	private boolean used;
	
	@Column(nullable = false)
	private String email;

}
