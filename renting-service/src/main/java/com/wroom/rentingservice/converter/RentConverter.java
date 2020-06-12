package com.wroom.rentingservice.converter;


import com.wroom.rentingservice.domain.RentRequest;
import com.wroom.rentingservice.domain.dto.RentRequestDTO;

public class RentConverter extends AbstractConverter{

	public static RentRequestDTO fromEntity(RentRequest entity) {
        return new RentRequestDTO(
                entity.getId(),
                entity.getStatus(),
                entity.getFromDate(),
                entity.getToDate(),
                entity.getRequestedUserId(),
                AdConverter.fromEntity(entity.getAd()),
                entity.getBundle() == null ? null : entity.getBundle().getId()
        );
    }

    public static RentRequest toEntity(RentRequestDTO dto) {
        RentRequest rentRequest = new RentRequest();
        rentRequest.setStatus(dto.getStatus());
        rentRequest.setFromDate(dto.getFromDate());
        rentRequest.setToDate(dto.getToDate());
        return rentRequest;
    }
}
