package com.wroom.adsservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date publishDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date availableFrom;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date availableTo;

    @Column
    private Double mileLimit;

    @Column
    private boolean mileLimitEnabled;

    @Column
    private boolean gps;

    @Column
    private boolean deleted;

    @Column
    private String address;

    //@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Column
    private Long vehicleId;

    @ManyToOne(fetch = FetchType.LAZY)
    private PriceList priceList;

    @ManyToOne(fetch = FetchType.LAZY)
    private Location location;

    @OneToMany(mappedBy = "ad")
	private Set<Comment> comments;
    
    @Column
    private Long localId;
    
    @Column
    private String ownerUsername;
}