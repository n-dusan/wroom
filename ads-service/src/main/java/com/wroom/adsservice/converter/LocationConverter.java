package com.wroom.adsservice.converter;


import com.wroom.adsservice.domain.Location;
import com.wroom.adsservice.domain.dto.LocationDTO;

public class LocationConverter extends AbstractConverter {

    public static LocationDTO fromEntity(Location location) {
        return new LocationDTO(
                location.getId(),
                location.getCountry(),
                location.getCity()
        );
    }

    public static Location toEntity(LocationDTO dto) {
        Location location = new Location();
        location.setCountry(dto.getCountry());
        location.setCity(dto.getCity());
        return location;
    }
}
