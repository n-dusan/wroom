package com.wroom.vehicleservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wroom.vehicleservice.converter.AMQPFeatureConverter;
import com.wroom.vehicleservice.converter.ModelTypeConverter;
import com.wroom.vehicleservice.domain.ModelType;
import com.wroom.vehicleservice.domain.dto.FeatureDTO;
import com.wroom.vehicleservice.exception.GeneralException;
import com.wroom.vehicleservice.producer.VehicleProducer;
import com.wroom.vehicleservice.producer.message.EntityEnum;
import com.wroom.vehicleservice.producer.message.OperationEnum;
import com.wroom.vehicleservice.repository.ModelTypeRepository;

@Service
public class ModelTypeService {

    private final ModelTypeRepository modelTypeRepository;
    private final BrandTypeService brandTypeService;
    private final VehicleProducer vehicleProducer;

    public ModelTypeService(ModelTypeRepository mtr,
                            BrandTypeService bts,
                            VehicleProducer vehicleProducer) {
        this.modelTypeRepository = mtr;
        this.brandTypeService = bts;
        this.vehicleProducer = vehicleProducer;
    }

    public List<ModelType> getAll() {
        List<ModelType> ret = new ArrayList<ModelType>();
        for(ModelType mt : modelTypeRepository.findAll()) {
            if(mt.isDeleted() == false) {
                ret.add(mt);
            }
        }
        return ret;
    }

    public ModelType save(FeatureDTO dto) {
        ModelType entity = this.modelTypeRepository.findByName(dto.getName());
        if(entity == null) {
            entity = ModelTypeConverter.toEntity(dto);
            entity.setBrandType(this.brandTypeService.findById(dto.getBrandId()));
            ModelType modelType = modelTypeRepository.save(entity);
            
            FeatureDTO feature = new FeatureDTO();
            feature.setName(modelType.getName());
            feature.setId(modelType.getId());
            feature.setBrandId(modelType.getBrandType().getId());
            
            //replicate to search service
            this.vehicleProducer.send(AMQPFeatureConverter.toVehicleMessage(feature, OperationEnum.CREATE, EntityEnum.MODEL_TYPE));
            
            return modelType;
        }
        else {
            return null;
        }
    }

//    public ModelType save(ModelType modelType) {
//        ModelType entity = this.modelTypeRepository.findByName(modelType.getName());
//        if(entity == null) {
//        	ModelType mt = modelTypeRepository.save(modelType);
//        	
//        	FeatureDTO feature = new FeatureDTO();
//            feature.setName(modelType.getName());
//            feature.setId(modelType.getId());
//            feature.setBrandId(modelType.getBrandType().getId());
//            
//            //replicate to search service
//            this.vehicleProducer.send(AMQPFeatureConverter.toFeatureMessage(feature, OperationEnum.CREATE, EntityEnum.MODEL_TYPE));
//        
//            return mt;
//        }
//        else {
//            return null;
//        }
//
//    }

    public ModelType findByName(String name) {
        return modelTypeRepository.findByName(name);
    }

    public ModelType findById(Long id){
        ModelType mt = modelTypeRepository.findById(id).orElseGet(null);
        return mt;
    }

    public void delete(String name) {
        ModelType modelType = findByName(name);
        modelType.setDeleted(true);
        modelTypeRepository.save(modelType);

        FeatureDTO feature = new FeatureDTO();
        feature.setName(modelType.getName());
        feature.setId(modelType.getId());
        
        //replicate to search service
        this.vehicleProducer.send(AMQPFeatureConverter.toVehicleMessage(feature, OperationEnum.DELETE, EntityEnum.MODEL_TYPE));
    }
    
    public void deleteById(Long id) {
        ModelType modelType = findById(id);
        modelType.setDeleted(true);
        modelTypeRepository.save(modelType);
     
        FeatureDTO feature = new FeatureDTO();
        feature.setName(modelType.getName());
        feature.setId(modelType.getId());
        
        //replicate to search service
        this.vehicleProducer.send(AMQPFeatureConverter.toVehicleMessage(feature, OperationEnum.DELETE, EntityEnum.MODEL_TYPE));
    
    }

    public ModelType update(ModelType mt, FeatureDTO featureDTO) {
        if(featureDTO == null) {
            throw new GeneralException("Forwarded DTO is null");
        }

        mt.setName(featureDTO.getName());
        ModelType modelType = this.modelTypeRepository.save(mt);
        
        FeatureDTO feature = new FeatureDTO();
        feature.setName(modelType.getName());
        feature.setId(modelType.getId());
        
        //replicate to search service
        this.vehicleProducer.send(AMQPFeatureConverter.toVehicleMessage(feature, OperationEnum.UPDATE, EntityEnum.MODEL_TYPE));
    
        return mt;
    }

}