package com.wroom.adsservice.converter;

import com.wroom.adsservice.domain.PriceList;
import com.wroom.adsservice.domain.dto.PriceListDTO;

public class PriceListConverter extends AbstractConverter {

    public static PriceListDTO fromEntity(PriceList entity) {
        return new PriceListDTO(
                entity.getId(),
                entity.getPricePerDay(),
                entity.getPricePerMile(),
                entity.getDiscount(),
                entity.getPriceCDW(),
                entity.getUserId());
    }

    public static PriceList toEntity(PriceListDTO dto) {
        PriceList priceList = new PriceList();
        priceList.setPricePerDay(dto.getPricePerDay());
        priceList.setPricePerMile(dto.getPricePerMile());
        priceList.setDiscount(dto.getDiscount());
        priceList.setPriceCDW(dto.getPriceCDW());
        priceList.setDeleted(false);
        priceList.setUserId(dto.getUserId());
        return priceList;
    }
}
