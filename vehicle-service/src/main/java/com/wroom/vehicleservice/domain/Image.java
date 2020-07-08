package com.wroom.vehicleservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "urlPath", nullable = false)
    private String urlPath;

    @ManyToOne(fetch = FetchType.LAZY)
    private Vehicle vehicle;
}
