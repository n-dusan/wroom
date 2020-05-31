package xwsagent.wroomagent.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;
import xwsagent.wroomagent.config.EndpointConfig;
import xwsagent.wroomagent.converter.AdConverter;
import xwsagent.wroomagent.converter.LocationConverter;
import xwsagent.wroomagent.domain.dto.AdDTO;
import xwsagent.wroomagent.domain.dto.LocationDTO;
import xwsagent.wroomagent.domain.dto.SearchCriteriaDTO;
import xwsagent.wroomagent.service.AdService;
import xwsagent.wroomagent.service.SearchService;

/**
 * Ad and Location CRUD.
 */
@RestController
@RequestMapping(value = EndpointConfig.AD_BASE_URL)
@Log4j2
public class AdController {

    private final AdService adService;
    private final SearchService searchService;

    public AdController(AdService adService, SearchService searchService) {
        this.adService = adService;
        this.searchService = searchService;
    }
    
    /**
     * GET /api/ads
     * @return list of ads
     */
    @GetMapping
    public ResponseEntity<List<AdDTO>> getAll() {
        return new ResponseEntity<>(AdConverter.fromEntityList(adService.findAll(), AdConverter::fromEntity),
                HttpStatus.OK);
    }


    /**
     * POST /api/ads
     * @param adDTO
     * @return newly created ad
     */
    @PostMapping
    public ResponseEntity<AdDTO> save(@Valid @RequestBody AdDTO adDTO) {
        return new ResponseEntity<>(AdConverter.fromEntity(adService.save(adDTO)), HttpStatus.OK);
    }

    /**
     * GET /api/ads/all/{user_id}
     * @param userId
     * @return list of ads for a specific user
     */
    @GetMapping("/all/{user}")
    public ResponseEntity<List<AdDTO>> getAllUser(@PathVariable("user") Long userId) {
        return new ResponseEntity<>(AdConverter.fromEntityList(adService.findAllActiveForUser(userId), AdConverter::fromEntity),
                HttpStatus.OK);
    }

    /**
     * GET api/ads/{ad}
     * @param adId
     * @return specified ad with forwarded id
     */
    @GetMapping("/{ad}")
    public ResponseEntity<AdDTO> getAd(@PathVariable("ad") Long adId) {
    	System.out.println("THIS IS AN ID: " + adId);
        return new ResponseEntity<>(AdConverter.fromEntity(adService.findById(adId)), HttpStatus.OK);
    }

    /**
     * PUT /api/ads/{ad}
     * @param adId
     * @param adDTO
     * @return updated ad
     */
    @PutMapping("/{ad}")
    public ResponseEntity<AdDTO> update(@PathVariable("ad") Long adId, @Valid @RequestBody AdDTO adDTO) {
        return new ResponseEntity<>(AdConverter.fromEntity(adService.update(adId, adDTO)), HttpStatus.OK);
    }

    /**
     * DELETE /api/ads/{ad}
     * @param adId
     * @return deleted ad
     */
    @DeleteMapping(value = "/{ad}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> delete(@PathVariable("ad") Long adId) {
        adService.delete(adId);
        return new ResponseEntity<>("Ad deleted.", HttpStatus.OK);
    }
    /**
     * POST /api/ads/location
     *
     * @param locationDTO
     * @return created location
     */
    @PostMapping("/location")
    public ResponseEntity<LocationDTO> saveLocation(@Valid @RequestBody LocationDTO locationDTO) {
        return new ResponseEntity<>(LocationConverter.fromEntity(
                adService.saveLocation(LocationConverter.toEntity(locationDTO))
        ), HttpStatus.OK);
    }

    /**
     * GET /api/ads/location
     *
     * @return all locations in the database
     */
    @GetMapping("/location")
    public ResponseEntity<List<LocationDTO>> getAllLocations() {
        return new ResponseEntity<>(
                LocationConverter.fromEntityList(adService.getAllLocations(), LocationConverter::fromEntity),
                HttpStatus.OK
        );
    }

    /**
     * GET /api/ads/location/{id}
     * @param locationId
     * @return specific location
     */
    @GetMapping("/location/{id}")
    public ResponseEntity<LocationDTO> getLocation(@PathVariable("id") Long locationId) {
        return new ResponseEntity<>(LocationConverter.fromEntity(adService.findLocationById(locationId)), HttpStatus.OK);
    }
    
    /**
     * POST /api/ads/search
     * @param search
     * @return
     */
    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody SearchCriteriaDTO search) {
    	return new ResponseEntity<>( AdConverter.fromEntityList(this.searchService.search(search), AdConverter::fromEntity), HttpStatus.OK);
        
    }

    @GetMapping(value="/count/{user_id}")
    public ResponseEntity<Integer> countAds(@PathVariable("user_id") Long user_id) {
        return new ResponseEntity<>(adService.countAds(user_id), HttpStatus.OK);
    }
}
