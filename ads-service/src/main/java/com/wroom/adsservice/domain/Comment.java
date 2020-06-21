package com.wroom.adsservice.domain;


import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.*;

@Entity
@Getter 
@Setter 
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Comment {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column(nullable = false)
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date commentDate;

    @Column
    private boolean deleted;

    @Column
    private boolean approved;
    
    @Column
    private Integer rate;

    @Column
    private String clientUsername;
    
    @Column
    private Long clientId;

    @ManyToOne(fetch = FetchType.EAGER)
    private Ad ad;

    @Column
    private Long replyId;
    
    @Column(nullable = false)
    private boolean reply;		// Marks if a comment is a reply to another comment

    @Column
    private Long localId;


}
