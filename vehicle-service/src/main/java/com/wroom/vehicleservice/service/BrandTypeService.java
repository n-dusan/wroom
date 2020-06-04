package com.wroom.vehicleservice.service;

import com.wroom.vehicleservice.converter.AMQPFeatureConverter;
import com.wroom.vehicleservice.domain.BrandType;
import com.wroom.vehicleservice.domain.dto.FeatureDTO;
import com.wroom.vehicleservice.exception.GeneralException;
import com.wroom.vehicleservice.producer.VehicleProducer;
import com.wroom.vehicleservice.producer.message.EntityEnum;
import com.wroom.vehicleservice.producer.message.OperationEnum;
import com.wroom.vehicleservice.repository.BrandTypeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrandTypeService {

    private final BrandTypeRepository brandTypeRepository;
    private final VehicleProducer vehicleProducer;

    public BrandTypeService(BrandTypeRepository brandTypeRepository,
    		VehicleProducer vehicleProducer) {
        this.brandTypeRepository = brandTypeRepository;
        this.vehicleProducer = vehicleProducer;
    }

    public List<BrandType> getAll() {
        List<BrandType> ret = new ArrayList<BrandType>();
        for(BrandType bt : brandTypeRepository.findAll()) {
            if(bt.isDeleted() == false) {
                ret.add(bt);
            }
        }
        return ret;
    }

    public BrandType save(BrandType brandType) {
        BrandType entity = this.brandTypeRepository.findByName(brandType.getName());
        if(entity == null) {
        	FeatureDTO feature = new FeatureDTO();
            feature.setName(brandType.getName());
            feature.setId(brandType.getId());
            
            // Notify search-service
            this.vehicleProducer.send(AMQPFeatureConverter.toVehicleMessage(feature, OperationEnum.CREATE, EntityEnum.BRAND_TYPE));
            return brandTypeRepository.save(brandType);
        }
        else {
            return null;
        }
    }

    public BrandType findByName(String name) {
        return brandTypeRepository.findByName(name);
    }

    public BrandType findById(Long id){
        BrandType bt = brandTypeRepository.findById(id).orElseGet(null);
        return bt;
    }

    public void delete(String name) {
        BrandType brandType = findByName(name);
        brandType.setDeleted(true);
        brandTypeRepository.save(brandType);
        
        FeatureDTO feature = new FeatureDTO();
        feature.setName(brandType.getName());
        feature.setId(brandType.getId());
        
        // Notify search-service
        this.vehicleProducer.send(AMQPFeatureConverter.toVehicleMessage(feature, OperationEnum.DELETE, EntityEnum.BRAND_TYPE));
        
    }
    
    public void deleteById(Long id) {
        BrandType brandType = findById(id);
        brandType.setDeleted(true);
        brandTypeRepository.save(brandType);
        
        FeatureDTO feature = new FeatureDTO();
        feature.setName(brandType.getName());
        feature.setId(brandType.getId());
        
        // Notify search-service
        this.vehicleProducer.send(AMQPFeatureConverter.toVehicleMessage(feature, OperationEnum.DELETE, EntityEnum.BRAND_TYPE));
        
    }

    public BrandType update(BrandType bt, FeatureDTO featureDTO) {
        if(featureDTO == null) {
            throw new GeneralException("Forwarded DTO is null");
        }
        bt.setName(featureDTO.getName());
        this.brandTypeRepository.save(bt);
        
        FeatureDTO feature = new FeatureDTO();
        feature.setName(bt.getName());
        feature.setId(bt.getId());
        
        // Notify search-service
        this.vehicleProducer.send(AMQPFeatureConverter.toVehicleMessage(feature, OperationEnum.UPDATE, EntityEnum.BRAND_TYPE));
        
        return bt;
    }
}