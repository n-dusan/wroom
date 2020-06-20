package xwsagent.wroomagent.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import xwsagent.wroomagent.controller.ModelTypeController;
import xwsagent.wroomagent.converter.ModelTypeConverter;
import xwsagent.wroomagent.domain.ModelType;
import xwsagent.wroomagent.domain.dto.FeatureDTO;
import xwsagent.wroomagent.exception.InvalidDataException;
import xwsagent.wroomagent.repository.ModelTypeRepository;

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
	
	public void delete(Long id) {
		ModelType modelType = findById(id);
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
