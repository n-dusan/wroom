package com.wroom.rentingservice.domain.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AdDTO {

    private Long id;

//    @NotNull(message = "an important field")
    private Long vehicleId;

//    @NotNull(message = "an important field")
    private Long priceListId;

//    @NotNull(message = "an important field")
//    @FutureOrPresent(message = "dates have to be in future")
    private Date availableFrom;

//    @NotNull(message = "an important field")
//    @Future(message = "dates have to be in future")
    private Date availableTo;

    private Double mileLimit;

    private boolean mileLimitEnabled;

//    @NotNull(message = "an important field")
    private Long locationId;

//    @NotNull(message = "an important field")
    private String address;

    private boolean gps;
    
//    private Double averageRate; 
}
