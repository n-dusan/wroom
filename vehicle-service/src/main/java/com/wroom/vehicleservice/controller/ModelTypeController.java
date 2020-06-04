package com.wroom.vehicleservice.controller;

import com.wroom.vehicleservice.config.EndpointConfig;
import com.wroom.vehicleservice.converter.ModelTypeConverter;
import com.wroom.vehicleservice.domain.ModelType;
import com.wroom.vehicleservice.domain.dto.FeatureDTO;
import com.wroom.vehicleservice.exception.APIError;
import com.wroom.vehicleservice.jwt.UserPrincipal;
import com.wroom.vehicleservice.service.ModelTypeService;
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
@RequestMapping(value = EndpointConfig.MODEL_TYPE_BASE_URL)
@Log4j2
public class ModelTypeController {

    private static final String LOG_CREATE = "action=create user=%s times=%s";
    private static final String LOG_UPDATE = "action=update user=%s times=%s";

    private final ModelTypeService modelTypeService;
    private final RequestCounter requestCounter;

    public ModelTypeController(ModelTypeService modelTypeService, RequestCounter requestCounter) {
        this.modelTypeService = modelTypeService;
        this.requestCounter = requestCounter;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> create(@Valid @RequestBody FeatureDTO modelTypeDTO, Authentication auth){
        String logContent = String.format(LOG_CREATE, ((UserPrincipal) auth.getPrincipal()).getUsername(), requestCounter.get(EndpointConfig.MODEL_TYPE_BASE_URL));

        try {
            log.info(logContent);
            return new ResponseEntity<>(
                    ModelTypeConverter.fromEntity(modelTypeService.save(modelTypeDTO)),
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
//        modelTypeService.delete(name);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
    
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        modelTypeService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<FeatureDTO>> getAll(){

        return new ResponseEntity<>(
                ModelTypeConverter.fromEntityList(modelTypeService.getAll(), ModelTypeConverter::fromEntity),
                HttpStatus.OK
        );
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<FeatureDTO> update(@RequestBody FeatureDTO featureDTO, @PathVariable("id") Long id, Authentication auth){
        ModelType mt = modelTypeService.findById(id);
        String logContent = String.format(LOG_UPDATE, ((UserPrincipal) auth.getPrincipal()).getUsername(), requestCounter.get(EndpointConfig.MODEL_TYPE_BASE_URL));
        log.info(logContent);
        return new ResponseEntity<>(
                ModelTypeConverter.fromEntity(modelTypeService.update(mt, featureDTO)),
                HttpStatus.OK
        );
    }

}