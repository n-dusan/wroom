package xwsagent.wroomagent.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xwsagent.wroomagent.domain.ModelType;
import xwsagent.wroomagent.domain.PriceList;
import xwsagent.wroomagent.domain.dto.FeatureDTO;
import xwsagent.wroomagent.exception.InvalidDataException;
import xwsagent.wroomagent.repository.ModelTypeRepository;

@Service
public class ModelTypeService {

	@Autowired
	ModelTypeRepository modelTypeRepository;
	
	public List<ModelType> getAll() {
		List<ModelType> ret = new ArrayList<ModelType>();
        for(ModelType mt : modelTypeRepository.findAll()) {
        	if(mt.isDeleted() == false) {
        		ret.add(mt);
        	}
        }
        return ret;
    }
	
	public ModelType save(ModelType modelType) {
	    return modelTypeRepository.save(modelType);
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
