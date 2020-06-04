package com.wroom.searchservice.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wroom.searchservice.converter.ModelTypeConverter;
import com.wroom.searchservice.domain.ModelType;
import com.wroom.searchservice.domain.dto.FeatureDTO;
import com.wroom.searchservice.exceptions.InvalidDataException;
import com.wroom.searchservice.repository.ModelTypeRepository;

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
			throw new InvalidDataException("Forwarded DTO is null");
		}	
		
		mt.setName(featureDTO.getName());
		this.modelTypeRepository.save(mt);
		return mt;
	}
	
}
