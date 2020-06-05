package com.wroom.searchservice.converter;


import com.wroom.searchservice.domain.RentRequest;
import com.wroom.searchservice.domain.dto.RentRequestDTO;
import com.wroom.searchservice.domain.dto.UserDTO;

public class RentConverter extends AbstractConverter{

	public static RentRequestDTO fromEntity(RentRequest entity) {
        return new RentRequestDTO(
                entity.getId(),
                entity.getStatus(),
                entity.getFromDate(),
                entity.getToDate(),
                new UserDTO(entity.getRequestedUser().getId(),
                        entity.getRequestedUser().getEmail(),
                        entity.getRequestedUser().getName(),
                        entity.getRequestedUser().getSurname(),
                        entity.getRequestedUser().isNonLocked(),
                        entity.getRequestedUser().isEnabled()),
                AdConverter.fromEntity(entity.getAd()),
                entity.getRequestedUser().getId()
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
