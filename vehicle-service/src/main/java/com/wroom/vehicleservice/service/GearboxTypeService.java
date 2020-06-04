package com.wroom.vehicleservice.service;

import com.wroom.vehicleservice.converter.AMQPFeatureConverter;
import com.wroom.vehicleservice.domain.GearboxType;
import com.wroom.vehicleservice.domain.dto.FeatureDTO;
import com.wroom.vehicleservice.exception.GeneralException;
import com.wroom.vehicleservice.producer.VehicleProducer;
import com.wroom.vehicleservice.producer.message.EntityEnum;
import com.wroom.vehicleservice.producer.message.OperationEnum;
import com.wroom.vehicleservice.repository.GearboxTypeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GearboxTypeService {


    private final GearboxTypeRepository gearboxTypeRepository;
    private final VehicleProducer vehicleProducer;

    public GearboxTypeService(GearboxTypeRepository gearboxTypeRepository,
    		VehicleProducer vehicleProducer) {
        this.gearboxTypeRepository = gearboxTypeRepository;
        this.vehicleProducer = vehicleProducer;
    }

    public List<GearboxType> getAll() {
        List<GearboxType> ret = new ArrayList<GearboxType>();
        for(GearboxType gt : gearboxTypeRepository.findAll()) {
            if(gt.isDeleted() == false) {
                ret.add(gt);
            }
        }
        return ret;
    }

    public GearboxType save(GearboxType gearboxType) {
        GearboxType entity = this.gearboxTypeRepository.findByName(gearboxType.getName());
        if(entity == null) {
        	GearboxType gt = gearboxTypeRepository.save(gearboxType);
        	FeatureDTO feature = new FeatureDTO();
            feature.setName(gearboxType.getName());
            feature.setId(gearboxType.getId());
            
            //replicate to search service
            this.vehicleProducer.send(AMQPFeatureConverter.toVehicleMessage(feature, OperationEnum.CREATE, EntityEnum.GEARBOX_TYPE));

            return gt;
        }
        else {
            return null;
        }

    }

    public GearboxType findByName(String name) {
        return gearboxTypeRepository.findByName(name);
    }

    public GearboxType findById(Long id){
        GearboxType gt = gearboxTypeRepository.findById(id).orElseGet(null);
        return gt;
    }

    public void delete(String name) {
        GearboxType gearboxType = findByName(name);
        gearboxType.setDeleted(true);
        gearboxTypeRepository.save(gearboxType);
        
        FeatureDTO feature = new FeatureDTO();
        feature.setName(gearboxType.getName());
        feature.setId(gearboxType.getId());
        
        //replicate to search service
        this.vehicleProducer.send(AMQPFeatureConverter.toVehicleMessage(feature, OperationEnum.DELETE, EntityEnum.GEARBOX_TYPE));

    }
    
    public void deleteById(Long id) {
        GearboxType gearboxType = findById(id);
        gearboxType.setDeleted(true);
        gearboxTypeRepository.save(gearboxType);

        FeatureDTO feature = new FeatureDTO();
        feature.setName(gearboxType.getName());
        feature.setId(gearboxType.getId());
        
        //replicate to search service
        this.vehicleProducer.send(AMQPFeatureConverter.toVehicleMessage(feature, OperationEnum.DELETE, EntityEnum.GEARBOX_TYPE));
    
    }

    public GearboxType update(GearboxType gt, FeatureDTO featureDTO) {
        if(featureDTO == null) {
            throw new GeneralException("Forwarded DTO is null");
        }

        gt.setName(featureDTO.getName());
        this.gearboxTypeRepository.save(gt);
        
        FeatureDTO feature = new FeatureDTO();
        feature.setName(gt.getName());
        feature.setId(gt.getId());
        
        //replicate to search service
        this.vehicleProducer.send(AMQPFeatureConverter.toVehicleMessage(feature, OperationEnum.UPDATE, EntityEnum.GEARBOX_TYPE));
    
        return gt;
    }
}