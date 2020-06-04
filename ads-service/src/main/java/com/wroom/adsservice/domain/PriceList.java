package com.wroom.adsservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PriceList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double pricePerDay;

    @Column
    private Double pricePerMile;

    @Column
    private Double priceCDW;

    @Column
    private Double discount;

    @Column(nullable = false)
    private boolean deleted;

    @Column
    private Long userId;

    @OneToMany(mappedBy = "priceList")
    private Set<Ad> ads;
}
