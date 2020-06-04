package com.wroom.vehicleservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wroom.vehicleservice.converter.AMQPFeatureConverter;
import com.wroom.vehicleservice.domain.BodyType;
import com.wroom.vehicleservice.domain.dto.FeatureDTO;
import com.wroom.vehicleservice.exception.GeneralException;
import com.wroom.vehicleservice.producer.VehicleProducer;
import com.wroom.vehicleservice.producer.message.EntityEnum;
import com.wroom.vehicleservice.producer.message.OperationEnum;
import com.wroom.vehicleservice.repository.BodyTypeRepository;

@Service
public class BodyTypeService {

    private final BodyTypeRepository bodyTypeRepository;
    private final VehicleProducer vehicleProducer;

    public BodyTypeService(BodyTypeRepository bodyTypeRepository,
                           VehicleProducer vehicleProducer) {
        this.bodyTypeRepository = bodyTypeRepository;
        this.vehicleProducer = vehicleProducer;
    }

    public List<BodyType> getAll() {
        List<BodyType> ret = new ArrayList<BodyType>();
        for(BodyType bt : bodyTypeRepository.findAll()) {
            if(bt.isDeleted() == false) {
                ret.add(bt);
            }
        }
        return ret;
    }

    public BodyType save(BodyType bodyType) {
        BodyType entity = this.bodyTypeRepository.findOneByName(bodyType.getName());
//        System.out.println("ENTITY IS " + entity.getName() + " " + entity.getId());
        if(entity == null) {
        	//replicate to search service
            FeatureDTO feature = new FeatureDTO();
            feature.setName(bodyType.getName());
            feature.setId(bodyType.getId());
            this.vehicleProducer.send(AMQPFeatureConverter.toVehicleMessage(feature, OperationEnum.CREATE, EntityEnum.BODY_TYPE));
            return bodyTypeRepository.save(bodyType);
        }
        else {
            return null;
        }
    }

    public BodyType findByName(String name) {
        return bodyTypeRepository.findOneByName(name);
    }

    public BodyType findById(Long id){
        BodyType bt = bodyTypeRepository.findById(id).orElseGet(null);
        return bt;
    }

    public void delete(String name) {
        BodyType bodyType = findByName(name);
        bodyType.setDeleted(true);
        bodyTypeRepository.save(bodyType);

        FeatureDTO feature = new FeatureDTO();
        feature.setName(bodyType.getName());
        feature.setId(bodyType.getId());
        
        //replicate to search service
        this.vehicleProducer.send(AMQPFeatureConverter.toVehicleMessage(feature, OperationEnum.DELETE, EntityEnum.BODY_TYPE));
    }
    
    public void deleteById(Long id) {
        BodyType bodyType = findById(id);
        bodyType.setDeleted(true);
        bodyTypeRepository.save(bodyType);

        FeatureDTO feature = new FeatureDTO();
        feature.setName(bodyType.getName());
        feature.setId(bodyType.getId());
        
        //replicate to search service
        this.vehicleProducer.send(AMQPFeatureConverter.toVehicleMessage(feature, OperationEnum.DELETE, EntityEnum.BODY_TYPE));
    }

    public BodyType update(BodyType bt, FeatureDTO featureDTO) {
        if(featureDTO == null) {
            throw new GeneralException("Forwarded DTO is null");
        }
        bt.setName(featureDTO.getName());
        this.bodyTypeRepository.save(bt);
        
        //replicate to search service
        FeatureDTO feature = new FeatureDTO();
        feature.setName(featureDTO.getName());
        feature.setId(bt.getId());
        this.vehicleProducer.send(AMQPFeatureConverter.toVehicleMessage(feature, OperationEnum.UPDATE, EntityEnum.BODY_TYPE));

        return bt;
    }

}