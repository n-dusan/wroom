package com.wroom.vehicleservice.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class GearboxType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "deleted")
    private boolean deleted;

    public GearboxType(String name) {
        this.name = name;
        this.deleted = false;
    }
}
