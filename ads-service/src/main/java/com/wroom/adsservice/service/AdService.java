package com.wroom.adsservice.service;

import com.wroom.adsservice.converter.AdConverter;
import com.wroom.adsservice.domain.Ad;
import com.wroom.adsservice.domain.Location;
import com.wroom.adsservice.domain.dto.AdDTO;
import com.wroom.adsservice.domain.dto.UserDTO;
import com.wroom.adsservice.domain.dto.VehicleDTO;
import com.wroom.adsservice.exception.GeneralException;
import com.wroom.adsservice.feigns.AuthClient;
import com.wroom.adsservice.feigns.VehicleClient;
import com.wroom.adsservice.repository.AdRepository;
import com.wroom.adsservice.repository.LocationRepository;
import com.wroom.adsservice.repository.PriceListRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class AdService {

    private final AdRepository adRepository;
    private final LocationRepository locationRepository;
    private final PriceListRepository priceListRepository;
    private final VehicleClient vehicleClient;
    private final AuthClient authClient;

    public AdService(LocationRepository locationRepository,
                     AdRepository adRepository,
                     PriceListRepository priceListRepository,
                     VehicleClient vehicleClient,
                     AuthClient authClient) {
        this.locationRepository = locationRepository;
        this.adRepository = adRepository;
        this.priceListRepository = priceListRepository;
        this.vehicleClient = vehicleClient;
        this.authClient = authClient;
    }

    public List<Ad> findAll() {
        return this.adRepository.findAll();
    }

    public List<Ad> findByVehicle(Long vehicleId) {
        return this.adRepository.findByVehicleId(vehicleId);
    }

    public Location findLocationById(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new GeneralException("Unable to find reference to " + id.toString() + " location"));
    }

    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Ad findById(Long id) {
        return adRepository.findById(id)
                .orElseThrow(() -> new GeneralException("Unable to find reference to " + id.toString() + " ad"));
    }

    public Ad save(AdDTO adDTO) {
        Ad ad = AdConverter.toEntity(adDTO);
       // ad.setVehicle(vehicleService.findById(adDTO.getVehicleId()));
        VehicleDTO vehicle = this.vehicleClient.findVehicleById(adDTO.getVehicleId());
        ad.setVehicleId(vehicle.getId());
        ad.setPriceList(priceListRepository.findOneById(adDTO.getPriceListId()));
        ad.setLocation(locationRepository.findOneById(adDTO.getLocationId()));
        ad.setPublishDate(Calendar.getInstance().getTime());
        return adRepository.save(ad);
    }

    public Ad update(Long adId, AdDTO dto) {
        Ad ad = AdConverter.toEntity(dto);
        ad.setId(adId);
        VehicleDTO vehicle = this.vehicleClient.findVehicleById(dto.getVehicleId());
        ad.setVehicleId(vehicle.getId());
        ad.setPriceList(priceListRepository.findOneById(dto.getPriceListId()));
        ad.setLocation(locationRepository.findOneById(dto.getLocationId()));
        ad.setPublishDate(Calendar.getInstance().getTime());
        return adRepository.save(ad);
    }

    public List<Ad> findAllActiveForUser(Long userId, String jwt) {
        List<Ad> ads = adRepository.findAllActive();
        List<Ad> bingo = new ArrayList<Ad>();
        List<VehicleDTO> vehicles = this.vehicleClient.getVehicles(jwt);
        for (Ad ad : ads) {
            for (VehicleDTO vehicle : vehicles) {
                System.out.println("Vehicle information " + vehicle);
                if(ad.getVehicleId() == vehicle.getId()) {
                    if(userId == vehicle.getOwnerId()) {
                        bingo.add(ad);
                    }
                }
            }
        }

        return bingo;
    }

    public void delete(Long adId) {
        Ad ad = findById(adId);
        ad.setDeleted(true);
        adRepository.save(ad);
    }

    public Integer countAds(Long user_id) {
        UserDTO user = this.authClient.findUser(user_id);
        //List<VehicleDTO> vehicles = this.vehicleClient.
        return adRepository.checkAdCountForUser(user.getId());
    }

//    public UserDTO getOwner(Long ad_id) {
//        return UserConverter.fromEntity(this.adRepository.findById(ad_id).get().getVehicle().getOwner());
//    }

}
