package com.wroom.adsservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PriceListDTO {

    private Long id;

    @NotNull(message = "Price per day may not be null.")
    private Double pricePerDay;

    @NotNull(message = "Price per mile may not be null.")
    private Double pricePerMile;

    @NotNull(message = "Discount not be null.")
    private Double discount;

    @NotNull(message = "Price CDW may not be null.")
    private Double priceCDW;

    @NotNull(message = "owner must be provided")
    private Long userId;
}
