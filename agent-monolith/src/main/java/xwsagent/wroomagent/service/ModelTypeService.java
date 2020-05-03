package xwsagent.wroomagent.service;

import java.util.ArrayList;
import java.util.List;

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
	
	public ModelType findById(Long id){
		ModelType mt = modelTypeRepository.findById(id).orElseGet(null);
		return mt;
	}
	
	public List<ModelType> findAll(){
		List<ModelType> list = new ArrayList<ModelType>();
		for(ModelType modelType :  modelTypeRepository.findAll()) {
			if(modelType.isDeleted() == false) {
				list.add(modelType);
			}		
		}
		return list;
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
	
	public boolean update(ModelType mt, ModelTypeDTO modelTypeDTO) {
		if(modelTypeDTO != null && (findByName(modelTypeDTO.getName()) == null)) {
			mt.setName(modelTypeDTO.getName());
			this.modelTypeRepository.save(mt);
			return true;
		}else {
			return false;
		}
	}
	
}
