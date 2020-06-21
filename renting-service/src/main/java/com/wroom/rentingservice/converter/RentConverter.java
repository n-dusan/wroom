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
                entity.getBundle() == null ? null : entity.getBundle().getId(),
                entity.getRentReport() == null ? null : entity.getRentReport().getId(),
                entity.getOwnerUsername() == null ? null : entity.getOwnerUsername(),
                entity.getLocalId() == null ? null: entity.getLocalId()
        );
    }

    public static RentRequest toEntity(RentRequestDTO dto) {
        RentRequest rentRequest = new RentRequest();
        rentRequest.setStatus(dto.getStatus());
        rentRequest.setFromDate(dto.getFromDate());
        rentRequest.setToDate(dto.getToDate());
        
        if(dto.getOwnerUsername() != null) {
        	rentRequest.setOwnerUsername(dto.getOwnerUsername());
        }
        
        if(dto.getLocalId() != null) {
        	rentRequest.setLocalId(dto.getLocalId());
        }
        
        return rentRequest;
    }
}
