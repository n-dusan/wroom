package com.wroom.adsservice.domain.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
