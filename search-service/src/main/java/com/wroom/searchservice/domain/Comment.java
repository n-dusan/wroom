package com.wroom.searchservice.domain;

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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column
    private Boolean deleted;

    @Column
    private Boolean approved;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private User client;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Ad ad;


}
