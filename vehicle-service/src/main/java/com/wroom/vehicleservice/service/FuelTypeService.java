package com.wroom.vehicleservice.service;

import com.wroom.vehicleservice.converter.AMQPFeatureConverter;
import com.wroom.vehicleservice.domain.FuelType;
import com.wroom.vehicleservice.domain.dto.FeatureDTO;
import com.wroom.vehicleservice.exception.GeneralException;
import com.wroom.vehicleservice.producer.VehicleProducer;
import com.wroom.vehicleservice.producer.message.EntityEnum;
import com.wroom.vehicleservice.producer.message.OperationEnum;
import com.wroom.vehicleservice.repository.FuelTypeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FuelTypeService {


    private final FuelTypeRepository fuelTypeRepository;
    private final VehicleProducer vehicleProducer;
    
    public FuelTypeService(FuelTypeRepository fuelTypeRepository,
    		VehicleProducer vehicleProducer) {
        this.fuelTypeRepository = fuelTypeRepository;
        this.vehicleProducer = vehicleProducer;
    }

    public List<FuelType> getAll() {
        List<FuelType> ret = new ArrayList<FuelType>();
        for(FuelType ft : fuelTypeRepository.findAll()) {
            if(ft.isDeleted() == false) {
                ret.add(ft);
            }
        }
        return ret;
    }

    public FuelType save(FuelType fuelType) {
        FuelType entity = this.fuelTypeRepository.findByName(fuelType.getName());
        if(entity == null) {
        	FuelType ft = fuelTypeRepository.save(fuelType);
        	FeatureDTO feature = new FeatureDTO();
            feature.setName(ft.getName());
            feature.setId(ft.getId());
            
            //replicate to search service
            this.vehicleProducer.send(AMQPFeatureConverter.toVehicleMessage(feature, OperationEnum.CREATE, EntityEnum.FUEL_TYPE));
            
            return ft;
        }
        else {
            return null;
        }
    }

    public FuelType findByName(String name) {
        return fuelTypeRepository.findByName(name);
    }

    public FuelType findById(Long id){
        FuelType ft = fuelTypeRepository.findById(id).orElseGet(null);
        return ft;
    }

    public void delete(String name) {
        FuelType fuelType = findByName(name);
        fuelType.setDeleted(true);
        fuelTypeRepository.save(fuelType);

    }
    
    public void deleteById(Long id) {
        FuelType fuelType = findById(id);
        fuelType.setDeleted(true);
        fuelTypeRepository.save(fuelType);
        FeatureDTO feature = new FeatureDTO();
        feature.setName(fuelType.getName());
        feature.setId(fuelType.getId());
        
        //replicate to search service
        this.vehicleProducer.send(AMQPFeatureConverter.toVehicleMessage(feature, OperationEnum.DELETE, EntityEnum.FUEL_TYPE));
    }

    public FuelType update(FuelType ft, FeatureDTO featureDTO) {
        if(featureDTO == null) {
            throw new GeneralException("Forwarded DTO is null");
        }

        ft.setName(featureDTO.getName());
        this.fuelTypeRepository.save(ft);
        
        FeatureDTO feature = new FeatureDTO();
        feature.setName(ft.getName());
        feature.setId(ft.getId());
        
        //replicate to search service
        this.vehicleProducer.send(AMQPFeatureConverter.toVehicleMessage(feature, OperationEnum.UPDATE, EntityEnum.FUEL_TYPE));
    
        return ft;
    }
}