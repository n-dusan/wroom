package xwsagent.wroomagent.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
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

@RestController
@RequestMapping(value = EndpointConfig.AD_BASE_URL)
@Log4j2
public class AdController {

    private final AdService adService;

    public AdController(AdService adService) {
        this.adService = adService;
    }


    @PostMapping
    public ResponseEntity<AdDTO> save(@Valid @RequestBody AdDTO adDTO) {
        return new ResponseEntity<>(AdConverter.fromEntity(adService.save((adDTO))), HttpStatus.OK);
    }

    /**
     * POST /api/ads/location
     *
     * @param locationDTO
     * @return created location
     */
    @PostMapping("/location")
    public ResponseEntity<LocationDTO> saveLocation(@Valid @RequestBody LocationDTO locationDTO) {
        System.out.println("Am i here?");
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
}
