package xwsagent.wroomagent.service;

import org.springframework.stereotype.Service;
import xwsagent.wroomagent.converter.AdConverter;
import xwsagent.wroomagent.domain.Ad;
import xwsagent.wroomagent.domain.Location;
import xwsagent.wroomagent.domain.dto.AdDTO;
import xwsagent.wroomagent.repository.AdRepository;
import xwsagent.wroomagent.repository.LocationRepository;
import xwsagent.wroomagent.repository.PriceListRepository;
import xwsagent.wroomagent.repository.VehicleRepository;

import java.util.Calendar;
import java.util.List;

@Service
public class AdService {

    private final AdRepository adRepository;
    private final LocationRepository locationRepository;
    private final PriceListRepository priceListRepository;
    private final VehicleRepository vehicleRepository;

    public AdService(LocationRepository locationRepository,
                     AdRepository adRepository,
                     PriceListRepository priceListRepository,
                     VehicleRepository vehicleRepository) {
        this.locationRepository = locationRepository;
        this.adRepository = adRepository;
        this.priceListRepository = priceListRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Ad save(AdDTO adDTO) {
        Ad ad = AdConverter.toEntity(adDTO);
        ad.setVehicle(vehicleRepository.findOneById(adDTO.getVehicleId()));
        ad.setPriceList(priceListRepository.findOneById(adDTO.getPriceListId()));
        ad.setLocation(locationRepository.findOneById(adDTO.getLocationId()));
        ad.setPublishDate(Calendar.getInstance().getTime());
        return adRepository.save(ad);
    }

    public List<Ad> findAllActive() {
        return null;
    }


}
