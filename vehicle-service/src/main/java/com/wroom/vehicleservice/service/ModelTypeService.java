package com.wroom.vehicleservice.service;

import com.wroom.vehicleservice.converter.ModelTypeConverter;
import com.wroom.vehicleservice.domain.ModelType;
import com.wroom.vehicleservice.domain.dto.FeatureDTO;
import com.wroom.vehicleservice.exception.GeneralException;
import com.wroom.vehicleservice.repository.ModelTypeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModelTypeService {

    private final ModelTypeRepository modelTypeRepository;
    private final BrandTypeService brandTypeService;

    public ModelTypeService(ModelTypeRepository mtr,
                            BrandTypeService bts) {
        this.modelTypeRepository = mtr;
        this.brandTypeService = bts;
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
            return modelTypeRepository.save(entity);
        }
        else {
            return null;
        }
    }

    public ModelType save(ModelType modelType) {
        ModelType entity = this.modelTypeRepository.findByName(modelType.getName());
        if(entity == null) {
            return modelTypeRepository.save(modelType);
        }
        else {
            return null;
        }

    }

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

    }

    public ModelType update(ModelType mt, FeatureDTO featureDTO) {
        if(featureDTO == null) {
            throw new GeneralException("Forwarded DTO is null");
        }

        mt.setName(featureDTO.getName());
        this.modelTypeRepository.save(mt);
        return mt;
    }

}