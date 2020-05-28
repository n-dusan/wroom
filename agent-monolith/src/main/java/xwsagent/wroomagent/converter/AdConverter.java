package xwsagent.wroomagent.converter;

import xwsagent.wroomagent.domain.Ad;
import xwsagent.wroomagent.domain.dto.AdDTO;


public class AdConverter extends AbstractConverter {

    public static AdDTO fromEntity(Ad entity) {
        return new AdDTO(
                entity.getId(),
                entity.getVehicle().getId(),
                entity.getPriceList().getId(),
                entity.getAvailableFrom(),
                entity.getAvailableTo(),
                entity.getMileLimit(),
                entity.isMileLimitEnabled(),
                entity.getLocation().getId(),
                entity.getAddress(),
                entity.isGps()
        );
    }

    public static Ad toEntity(AdDTO dto) {
        Ad ad = new Ad();
        ad.setAvailableFrom(dto.getAvailableFrom());
        ad.setAvailableTo(dto.getAvailableTo());
        if(dto.isMileLimitEnabled()) {
            ad.setMileLimit(dto.getMileLimit());
            ad.setMileLimitEnabled(true);
        } else {
            ad.setMileLimitEnabled(false);
        }
        ad.setAddress(dto.getAddress());
        ad.setGps(dto.isGps());
        return ad;
    }
}
