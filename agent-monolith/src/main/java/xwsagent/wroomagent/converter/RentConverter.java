package xwsagent.wroomagent.converter;


import java.util.HashSet;
import java.util.Set;

import xwsagent.wroomagent.domain.Ad;
import xwsagent.wroomagent.domain.RentRequest;
import xwsagent.wroomagent.domain.dto.AdDTO;
import xwsagent.wroomagent.domain.dto.RentRequestDTO;
import xwsagent.wroomagent.domain.dto.UserDTO;

public class RentConverter extends AbstractConverter{

	public static RentRequestDTO fromEntity(RentRequest entity) {
		Set<AdDTO> ads = new HashSet<AdDTO>();
		for(Ad a : entity.getAds()) {
			ads.add(AdConverter.fromEntity(a));
		}
        return new RentRequestDTO(
                entity.getId(),
                entity.getStatus(),
                entity.getFromDate(),
                entity.getToDate(),
                new UserDTO(entity.getRequestedUser().getId(), entity.getRequestedUser().getEmail(), entity.getRequestedUser().getPassword(), entity.getRequestedUser().getName(), entity.getRequestedUser().getSurname(), entity.getRequestedUser().isNonLocked()),
                ads
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
