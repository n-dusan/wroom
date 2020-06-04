package com.wroom.vehicleservice.controller;

import com.wroom.vehicleservice.config.EndpointConfig;
import com.wroom.vehicleservice.converter.FuelTypeConverter;
import com.wroom.vehicleservice.domain.FuelType;
import com.wroom.vehicleservice.domain.dto.FeatureDTO;
import com.wroom.vehicleservice.exception.APIError;
import com.wroom.vehicleservice.jwt.UserPrincipal;
import com.wroom.vehicleservice.service.FuelTypeService;
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
@RequestMapping(value = EndpointConfig.FUEL_TYPE_BASE_URL)
@Log4j2
public class FuelTypeController {

    private static final String LOG_CREATE = "action=create user=%s times=%s";
    private static final String LOG_UPDATE = "action=update user=%s times=%s";

    private final FuelTypeService fuelTypeService;
    private final RequestCounter requestCounter;

    public FuelTypeController(FuelTypeService fuelTypeService, RequestCounter requestCounter) {
        this.fuelTypeService = fuelTypeService;
        this.requestCounter = requestCounter;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> create(@Valid @RequestBody FeatureDTO fuelTypeDTO, Authentication auth)  {
        String logContent = String.format(LOG_CREATE, ((UserPrincipal) auth.getPrincipal()).getUsername(), requestCounter.get(EndpointConfig.FUEL_TYPE_BASE_URL));

        try {
            log.info(logContent);
            return new ResponseEntity<>(
                    FuelTypeConverter.fromEntity(fuelTypeService.save(FuelTypeConverter.toEntity(fuelTypeDTO))),
                    HttpStatus.CREATED
            );
        } catch(NullPointerException e) {
            log.error(logContent);
            return new ResponseEntity<>(
                    new APIError(HttpStatus.BAD_REQUEST, "Name exists", Collections.singletonList("Name exists")), HttpStatus.BAD_REQUEST);
        }
    }

//    @DeleteMapping(value = "/{name}")
//    public ResponseEntity<Void> delete(@PathVariable("name") String name){
//        fuelTypeService.delete(name);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
    
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        fuelTypeService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<FeatureDTO>> getAll(){

        return new ResponseEntity<>(
                FuelTypeConverter.fromEntityList(fuelTypeService.getAll(), FuelTypeConverter::fromEntity),
                HttpStatus.OK
        );
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<FeatureDTO> update(@RequestBody FeatureDTO featureDTO, @PathVariable("id") Long id, Authentication auth){
        FuelType ft = fuelTypeService.findById(id);
        String logContent = String.format(LOG_UPDATE, ((UserPrincipal) auth.getPrincipal()).getUsername(), requestCounter.get(EndpointConfig.FUEL_TYPE_BASE_URL));
        log.info(logContent);
        return new ResponseEntity<>(
                FuelTypeConverter.fromEntity(fuelTypeService.update(ft, featureDTO)),
                HttpStatus.OK
        );
    }
}