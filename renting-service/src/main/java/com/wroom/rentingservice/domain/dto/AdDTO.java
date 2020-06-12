package com.wroom.rentingservice.domain.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AdDTO {

    private Long id;
    private Long vehicleId;
    private Long priceListId;
    private Long locationId;

    private Date availableFrom;
    private Date availableTo;
    private Double mileLimit;
    private boolean mileLimitEnabled;
    private String address;
    private boolean gps;

}
