package com.wroom.vehicleservice.controller;


import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import com.wroom.vehicleservice.config.EndpointConfig;
import com.wroom.vehicleservice.converter.BodyTypeConverter;
import com.wroom.vehicleservice.domain.BodyType;
import com.wroom.vehicleservice.domain.dto.FeatureDTO;
import com.wroom.vehicleservice.exception.APIError;
import com.wroom.vehicleservice.jwt.UserPrincipal;
import com.wroom.vehicleservice.service.BodyTypeService;
import com.wroom.vehicleservice.utils.RequestCounter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = EndpointConfig.BODY_TYPE_BASE_URL)
@Log4j2
public class BodyTypeController {

    private static final String LOG_CREATE = "action=create user=%s times=%s";
    private static final String LOG_UPDATE = "action=update user=%s times=%s";

    private final BodyTypeService bodyTypeService;
    private final RequestCounter requestCounter;

    public BodyTypeController(BodyTypeService bodyTypeService, RequestCounter requestCounter) {
        this.bodyTypeService = bodyTypeService;
        this.requestCounter = requestCounter;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> create(@Valid @RequestBody FeatureDTO featureDTO, Authentication auth) {

        String logContent = String.format(LOG_CREATE, ((UserPrincipal) auth.getPrincipal()).getUsername(), requestCounter.get(EndpointConfig.BODY_TYPE_BASE_URL));
        log.info(logContent);
        try {
            return new ResponseEntity<>(
                    BodyTypeConverter.fromEntity(bodyTypeService.save(BodyTypeConverter.toEntity(featureDTO))),
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
//        bodyTypeService.delete(name);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//    
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id){
        bodyTypeService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<FeatureDTO>> getBodyTypes(){

        return new ResponseEntity<>(
                BodyTypeConverter.fromEntityList(bodyTypeService.getAll(), BodyTypeConverter::fromEntity),
                HttpStatus.OK
        );
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@RequestBody FeatureDTO featureDTO, @PathVariable("id") Long id, Authentication auth){
        
        String logContent = String.format(LOG_UPDATE, ((UserPrincipal) auth.getPrincipal()).getUsername(), requestCounter.get(EndpointConfig.BODY_TYPE_BASE_URL));
        log.info(logContent);
        return new ResponseEntity<>(
                BodyTypeConverter.fromEntity(bodyTypeService.update(featureDTO)),
                HttpStatus.OK
        );
    }
}
