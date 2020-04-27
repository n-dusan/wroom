package xwsagent.wroomagent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xwsagent.wroomagent.domain.ModelType;
import xwsagent.wroomagent.dto.ModelTypeDTO;
import xwsagent.wroomagent.repository.ModelTypeRepository;

@Service
public class ModelTypeService {

	@Autowired
	ModelTypeRepository modelTypeRepository;
	
	public ModelType findByName(String name) {
		return modelTypeRepository.findByName(name);
	}
	
	public boolean create(ModelTypeDTO modelTypeDTO) {
		if(findByName(modelTypeDTO.getName()) == null) {
			ModelType modelType = new ModelType();
			modelType.setName(modelTypeDTO.getName());
			modelTypeRepository.save(modelType);
			return true;
		}else {
			return false;
		}
	}
	
	public boolean delete(String name) {
		if(findByName(name) != null) {
			ModelType modelType = findByName(name);
			modelType.setDeleted(true);
			modelTypeRepository.save(modelType);
			return true;
		}else {
			return false;
		}
		
	}
}
