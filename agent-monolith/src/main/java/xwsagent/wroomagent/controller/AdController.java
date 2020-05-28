package xwsagent.wroomagent.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xwsagent.wroomagent.config.EndpointConfig;
import xwsagent.wroomagent.converter.AdConverter;
import xwsagent.wroomagent.converter.LocationConverter;
import xwsagent.wroomagent.domain.dto.AdDTO;
import xwsagent.wroomagent.domain.dto.LocationDTO;
import xwsagent.wroomagent.service.AdService;

import javax.validation.Valid;
import java.util.List;

/**
 * Has Ad and Location CRUD.
 */
@RestController
@RequestMapping(value = EndpointConfig.AD_BASE_URL)
@Log4j2
public class AdController {

    private final AdService adService;

    public AdController(AdService adService) {
        this.adService = adService;
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
}
