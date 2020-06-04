package com.wroom.vehicleservice.controller;

import com.wroom.vehicleservice.config.EndpointConfig;
import com.wroom.vehicleservice.converter.GearboxTypeConverter;
import com.wroom.vehicleservice.domain.GearboxType;
import com.wroom.vehicleservice.domain.dto.FeatureDTO;
import com.wroom.vehicleservice.exception.APIError;
import com.wroom.vehicleservice.jwt.UserPrincipal;
import com.wroom.vehicleservice.service.GearboxTypeService;
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
@RequestMapping(value = EndpointConfig.GEARBOX_TYPE_BASE_URL)
@Log4j2
public class GearboxTypeController {

    private static final String LOG_CREATE = "action=create user=%s times=%s";
    private static final String LOG_UPDATE = "action=update user=%s times=%s";

    private final GearboxTypeService gearboxTypeService;
    private final RequestCounter requestCounter;

    public GearboxTypeController(GearboxTypeService gearboxTypeService, RequestCounter requestCounter) {
        this.gearboxTypeService = gearboxTypeService;
        this.requestCounter = requestCounter;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> create(@Valid @RequestBody FeatureDTO gearboxTypeDTO, Authentication auth) {
        String logContent = String.format(LOG_CREATE, ((UserPrincipal) auth.getPrincipal()).getUsername(), requestCounter.get(EndpointConfig.GEARBOX_TYPE_BASE_URL));

        try {
            log.info(logContent);
            return new ResponseEntity<>(
                    GearboxTypeConverter.fromEntity(gearboxTypeService.save(GearboxTypeConverter.toEntity(gearboxTypeDTO))),
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
//        gearboxTypeService.delete(name);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
    
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        gearboxTypeService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<FeatureDTO>> getAll() {

        return new ResponseEntity<>(
                GearboxTypeConverter.fromEntityList(gearboxTypeService.getAll(), GearboxTypeConverter::fromEntity),
                HttpStatus.OK
        );
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<FeatureDTO> update(@RequestBody FeatureDTO featureDTO, @PathVariable("id") Long id, Authentication auth){
        String logContent = String.format(LOG_UPDATE, ((UserPrincipal) auth.getPrincipal()).getUsername(), requestCounter.get(EndpointConfig.GEARBOX_TYPE_BASE_URL));
        log.info(logContent);
        return new ResponseEntity<>(
                GearboxTypeConverter.fromEntity(gearboxTypeService.update(featureDTO)),
                HttpStatus.OK
        );
    }

}