package xwsagent.wroomagent.converter;


import xwsagent.wroomagent.domain.RentRequest;
import xwsagent.wroomagent.domain.dto.RentRequestDTO;
import xwsagent.wroomagent.domain.dto.UserDTO;

public class RentConverter extends AbstractConverter{

    public static RentRequestDTO fromEntity(RentRequest entity) {
        return new RentRequestDTO(
                entity.getId(),
                entity.getStatus(),
                entity.getFromDate(),
                entity.getToDate(),
                entity.getRequestedUser() == null ? null : entity.getRequestedUser().getId(),
                AdConverter.fromEntity(entity.getAd()),
                entity.getBundle() == null ? null : entity.getBundle().getId(),
                entity.getRentReport() == null ? null : entity.getRentReport().getId()
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
