package com.wroom.vehicleservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private Double mileage;

    @Column(nullable = false)
    private Integer childSeats;

    @Column(nullable = false)
    private Boolean cdw;

    @Column
    private boolean deleted;

    private Long ownerId;

//	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
//	private BrandType brandType;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private ModelType modelType;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private FuelType fuelType;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private BodyType bodyType;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private GearboxType gearboxType;

    @OneToMany(mappedBy = "vehicle")
    private Set<Image> images;
    
    @Column
    private Long localId;
    
    @Column
    private String ownerUsername;

}