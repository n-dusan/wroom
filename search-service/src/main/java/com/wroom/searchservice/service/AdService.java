package com.wroom.searchservice.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wroom.searchservice.converter.AdConverter;
import com.wroom.searchservice.converter.UserConverter;
import com.wroom.searchservice.domain.Ad;
import com.wroom.searchservice.domain.Location;
import com.wroom.searchservice.domain.User;
import com.wroom.searchservice.domain.dto.AdDTO;
import com.wroom.searchservice.domain.dto.UserDTO;
import com.wroom.searchservice.exceptions.InvalidReferenceException;
import com.wroom.searchservice.repository.AdRepository;
import com.wroom.searchservice.repository.LocationRepository;
import com.wroom.searchservice.repository.PriceListRepository;
import com.wroom.searchservice.repository.VehicleRepository;

@Service
public class AdService {

    private final AdRepository adRepository;
    private final LocationRepository locationRepository;
    private final PriceListRepository priceListRepository;
    private final VehicleService vehicleService;
    private final UserService userService;

    public AdService(LocationRepository locationRepository,
                     AdRepository adRepository,
                     PriceListRepository priceListRepository,
                     VehicleRepository vehicleRepository,
                     VehicleService vehicleService,
                     UserService userService) {
        this.locationRepository = locationRepository;
        this.adRepository = adRepository;
        this.priceListRepository = priceListRepository;
        this.vehicleService = vehicleService;
        this.userService = userService;
    }

    public List<Ad> findAll() {
    	return this.adRepository.findAll();
    }
    
    public Location findLocationById(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new InvalidReferenceException("Unable to find reference to " + id.toString() + " location"));
    }

    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Ad findById(Long id) {
        return adRepository.findById(id)
                .orElseThrow(() -> new InvalidReferenceException("Unable to find reference to " + id.toString() + " ad"));
    }

    public Ad save(AdDTO adDTO) {
        Ad ad = AdConverter.toEntity(adDTO);
        ad.setVehicle(vehicleService.findById(adDTO.getVehicleId()));
        ad.setPriceList(priceListRepository.findOneById(adDTO.getPriceListId()));
        ad.setLocation(locationRepository.findOneById(adDTO.getLocationId()));
        ad.setPublishDate(Calendar.getInstance().getTime());
        return adRepository.save(ad);
    }

    public Ad update(Long adId, AdDTO dto) {
        Ad ad = AdConverter.toEntity(dto);
        ad.setId(adId);
        ad.setVehicle(vehicleService.findById(dto.getVehicleId()));
        ad.setPriceList(priceListRepository.findOneById(dto.getPriceListId()));
        ad.setLocation(locationRepository.findOneById(dto.getLocationId()));
        ad.setPublishDate(Calendar.getInstance().getTime());
        return adRepository.save(ad);
    }

    public List<Ad> findAllActiveForUser(Long userId) {
        return adRepository.findAllActiveUser(userId);
    }

    public void delete(Long adId) {
        Ad ad = findById(adId);
        ad.setDeleted(true);
        adRepository.save(ad);
    }

    public Integer countAds(Long user_id) {
        User user = userService.findById(user_id);
        return adRepository.checkAdCountForUser(user.getId());
    }
    
}
