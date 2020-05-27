package xwsagent.wroomagent.service;

import org.springframework.stereotype.Service;
import xwsagent.wroomagent.domain.Location;
import xwsagent.wroomagent.domain.PriceList;
import xwsagent.wroomagent.repository.LocationRepository;

import java.util.List;

@Service
public class AdService {

    private final LocationRepository locationRepository;

    public AdService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }


}
