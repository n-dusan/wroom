package com.wroom.vehicleservice.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ModelType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private BrandType brandType;

    @Column(name = "deleted")
    private boolean deleted;

    public ModelType(String name) {
        this.name = name;
        this.deleted = false;
    }

}
