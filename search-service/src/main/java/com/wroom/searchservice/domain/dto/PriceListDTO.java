package com.wroom.searchservice.domain.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
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

}
