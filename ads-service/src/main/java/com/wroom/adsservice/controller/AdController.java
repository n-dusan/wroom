package com.wroom.adsservice.controller;

import com.wroom.adsservice.config.EndpointConfig;

import com.wroom.adsservice.converter.AdConverter;
import com.wroom.adsservice.converter.CommentConverter;
import com.wroom.adsservice.converter.LocationConverter;
import com.wroom.adsservice.domain.Comment;
import com.wroom.adsservice.domain.dto.AdDTO;
import com.wroom.adsservice.domain.dto.CommentDTO;
import com.wroom.adsservice.domain.dto.LocationDTO;
import com.wroom.adsservice.jwt.UserPrincipal;
import com.wroom.adsservice.repository.CommentRepository;
import com.wroom.adsservice.service.AdService;
import com.wroom.adsservice.utils.RequestCounter;
import lombok.extern.log4j.Log4j2;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = EndpointConfig.AD_BASE_URL)
@Log4j2
public class AdController {

    private static final String POST_AD = "action=create user=%s times=%s";
    private static final String UPDATE_AD = "action=update user=%s times=%s";

    private final AdService adService;
    private final RequestCounter requestCounter;
    private final CommentRepository commentRepository;

    public AdController(AdService adService, RequestCounter requestCounter, CommentRepository commentRepository) {
        this.adService = adService;
        this.requestCounter = requestCounter;
        this.commentRepository = commentRepository;
    }

    /**
     * GET /api/ads
     *
     * @return list of ads
     */
    @GetMapping
    public ResponseEntity<List<AdDTO>> getAll() {
        return new ResponseEntity<>(AdConverter.fromEntityList(adService.findAll(), AdConverter::fromEntity),
                HttpStatus.OK);
    }

    /**
     * POST /api/ads
     *
     * @param adDTO
     * @return newly created ad
     */
    @PostMapping
    public ResponseEntity<AdDTO> save(@Valid @RequestBody AdDTO adDTO, Authentication auth) {
        String logContent = String.format(POST_AD, ((UserPrincipal) auth.getPrincipal()).getUsername(), requestCounter.get(EndpointConfig.AD_BASE_URL));
        log.info(logContent);
        return new ResponseEntity<>(AdConverter.fromEntity(adService.save(adDTO)), HttpStatus.OK);
    }

    /**
     * GET /api/ads/all/{user_id}
     *
     * @param userId
     * @return list of ads for a specific user
     */
    @GetMapping("/all/{user}")
    public ResponseEntity<List<AdDTO>> getAllUser(@PathVariable("user") Long userId, HttpServletRequest request) {
        return new ResponseEntity<>(
                AdConverter.fromEntityList(adService.findAllActiveForUser(userId, request.getHeader("Authorization")), AdConverter::fromEntity),
                HttpStatus.OK);
    }

    /**
     * GET api/ads/{ad}
     *
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
     *
     * @param adId
     * @param adDTO
     * @return updated ad
     */
    @PutMapping("/{ad}")
    public ResponseEntity<AdDTO> update(@PathVariable("ad") Long adId, @Valid @RequestBody AdDTO adDTO, Authentication auth) {
        String logContent = String.format(UPDATE_AD, ((UserPrincipal) auth.getPrincipal()).getUsername(), requestCounter.get(EndpointConfig.AD_BASE_URL));
        log.info(logContent);
        return new ResponseEntity<>(AdConverter.fromEntity(adService.update(adId, adDTO)), HttpStatus.OK);
    }

    /**
     * DELETE /api/ads/{ad}
     *
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
        return new ResponseEntity<>(
                LocationConverter.fromEntity(adService.saveLocation(LocationConverter.toEntity(locationDTO))),
                HttpStatus.OK);
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
                HttpStatus.OK);
    }

    /**
     * GET /api/ads/location/{id}
     *
     * @param locationId
     * @return specific location
     */
    @GetMapping("/location/{id}")
    public ResponseEntity<LocationDTO> getLocation(@PathVariable("id") Long locationId) {
        return new ResponseEntity<>(LocationConverter.fromEntity(adService.findLocationById(locationId)),
                HttpStatus.OK);
    }

    @GetMapping(value = "/count/{user_id}")
    public ResponseEntity<Integer> countAds(@PathVariable("user_id") Long user_id) {
        return new ResponseEntity<>(adService.countAds(user_id), HttpStatus.OK);
    }


    @GetMapping("/vehicle/{id}")
    public ResponseEntity<List<AdDTO>> findByVehicle(@PathVariable("id") Long vehicleId) {
        System.out.println("I GOT HERE" + vehicleId);
        return new ResponseEntity<>(null, HttpStatus.OK);
        //return new ResponseEntity<>(AdConverter.fromEntityList(adService.findByVehicle(vehicleId), AdConverter::fromEntity), HttpStatus.OK);
    }
    

    @GetMapping("/comments")
    public ResponseEntity<List<CommentDTO>> getAllComments() {
    	
        return new ResponseEntity<>(CommentConverter.fromEntityList(adService.getComments(), CommentConverter::fromEntity),
                HttpStatus.OK);
    }
    
    @PostMapping(value = "/confirm/{id}")
	public ResponseEntity<?> confirm(@PathVariable("id") Long id) {
		Comment comment = adService.findByCommentId(id);
		if (comment.isApproved() == false) {
			adService.confirm(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}
    
    @PostMapping(value = "/refuse/{id}")
	public ResponseEntity<?> refuse(@PathVariable("id") Long id) {
		Comment comment = adService.findByCommentId(id);
		if (comment.isApproved() == false) {
			adService.refuse(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}
}
