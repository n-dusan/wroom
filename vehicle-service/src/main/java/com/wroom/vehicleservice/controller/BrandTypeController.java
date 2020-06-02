package com.wroom.vehicleservice.controller;

import com.wroom.vehicleservice.config.EndpointConfig;
import com.wroom.vehicleservice.converter.BrandTypeConverter;
import com.wroom.vehicleservice.domain.BrandType;
import com.wroom.vehicleservice.domain.dto.FeatureDTO;
import com.wroom.vehicleservice.exception.APIError;
import com.wroom.vehicleservice.jwt.UserPrincipal;
import com.wroom.vehicleservice.service.BrandTypeService;
import com.wroom.vehicleservice.utils.RequestCounter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = EndpointConfig.BRAND_TYPE_BASE_URL)
@Log4j2
public class BrandTypeController {

    private static final String LOG_CREATE = "action=create user=%s times=%s";
    private static final String LOG_UPDATE = "action=update user=%s times=%s";

    private final BrandTypeService brandTypeService;
    private final RequestCounter requestCounter;

    public BrandTypeController(BrandTypeService brandTypeService, RequestCounter requestCounter) {
        this.brandTypeService = brandTypeService;
        this.requestCounter = requestCounter;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> create(@Valid @RequestBody FeatureDTO brandTypeDTO, Authentication auth){
        String logContent = String.format(LOG_CREATE, ((UserPrincipal) auth.getPrincipal()).getUsername(), requestCounter.get(EndpointConfig.BRAND_TYPE_BASE_URL));

        try {
            log.info(logContent);
            return new ResponseEntity<>(
                    BrandTypeConverter.fromEntity(brandTypeService.save(BrandTypeConverter.toEntity(brandTypeDTO))),
                    HttpStatus.CREATED
            );
        } catch(NullPointerException e) {
            log.error(logContent);
            return new ResponseEntity<>(
                    new APIError(HttpStatus.BAD_REQUEST, "Name exists", Collections.singletonList("Name exists")), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{name}")
    public ResponseEntity<Void> delete(@PathVariable("name") String name){
        brandTypeService.delete(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<FeatureDTO>> getModelTypes(){

        return new ResponseEntity<>(
                BrandTypeConverter.fromEntityList(brandTypeService.getAll(), BrandTypeConverter::fromEntity),
                HttpStatus.OK
        );
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@RequestBody FeatureDTO featureDTO, @PathVariable("id") Long id, Authentication auth){
        BrandType bt = brandTypeService.findById(id);
        String logContent = String.format(LOG_UPDATE, ((UserPrincipal) auth.getPrincipal()).getUsername(), requestCounter.get(EndpointConfig.BRAND_TYPE_BASE_URL));
        log.info(logContent);
        return new ResponseEntity<>(
                BrandTypeConverter.fromEntity(brandTypeService.update(bt, featureDTO)),
                HttpStatus.OK
        );
    }
}