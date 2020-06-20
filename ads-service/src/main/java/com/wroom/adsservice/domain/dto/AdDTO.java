package com.wroom.adsservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdDTO {

    private Long id;

    @NotNull(message = "an important field")
    private Long vehicleId;

    @NotNull(message = "an important field")
    private Long priceListId;

    @NotNull(message = "an important field")
    @FutureOrPresent(message = "dates have to be in future")
    private Date availableFrom;

    @NotNull(message = "an important field")
    @Future(message = "dates have to be in future")
    private Date availableTo;

    private Double mileLimit;

    private boolean mileLimitEnabled;

    @NotNull(message = "an important field")
    private Long locationId;

    @NotNull(message = "an important field")
    private String address;

    private boolean gps;

    private Double averageRate;
    
    private Long localId;
}
