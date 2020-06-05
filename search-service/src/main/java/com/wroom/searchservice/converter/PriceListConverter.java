package com.wroom.searchservice.converter;

import com.wroom.searchservice.domain.PriceList;
import com.wroom.searchservice.domain.dto.PriceListDTO;

public class PriceListConverter extends AbstractConverter {

    public static PriceListDTO fromEntity(PriceList entity) {
        return new PriceListDTO(
                entity.getId(),
                entity.getPricePerDay(),
                entity.getPricePerMile(),
                entity.getDiscount(),
                entity.getPriceCDW());
    }

    public static PriceList toEntity(PriceListDTO dto) {
        PriceList priceList = new PriceList();
        priceList.setPricePerDay(dto.getPricePerDay());
        priceList.setPricePerMile(dto.getPricePerMile());
        priceList.setDiscount(dto.getDiscount());
        priceList.setPriceCDW(dto.getPriceCDW());
        priceList.setDeleted(false);

        if(dto.getId() != null) {
            priceList.setId(dto.getId());
        }

        return priceList;
    }
}
