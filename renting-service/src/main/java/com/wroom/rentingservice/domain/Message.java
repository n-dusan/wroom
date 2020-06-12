package com.wroom.rentingservice.domain;

import java.util.Date;

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
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Message {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(nullable = false)
	private Long fromUserId;
	
	@Column(nullable = false)
	private Long toUserId;
	
	@Column(nullable = false)
	private Long rentRequestId;
	
	@Column
	private String title;
	
	@Column(nullable = false)
	private String content;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date date;
	

}
