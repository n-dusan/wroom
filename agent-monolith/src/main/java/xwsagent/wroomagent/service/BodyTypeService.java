package xwsagent.wroomagent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xwsagent.wroomagent.domain.BodyType;
import xwsagent.wroomagent.dto.BodyTypeDTO;
import xwsagent.wroomagent.repository.BodyTypeRepository;

@Service
public class BodyTypeService {
	
	@Autowired
	BodyTypeRepository bodyTypeRepository;
	
	public BodyType findByName(String name) {
		return bodyTypeRepository.findByName(name);
	}
	
	public boolean create(BodyTypeDTO bodyTypeDTO) {
		if(findByName(bodyTypeDTO.getName()) == null) {
			BodyType bodyType = new BodyType();
			bodyType.setName(bodyTypeDTO.getName());;
			bodyTypeRepository.save(bodyType);
			return true;
		}else {
			return false;
		}
	}
	
	public boolean delete(String name) {
		if(findByName(name) != null) {
			BodyType bodyType = findByName(name);
			bodyType.setDeleted(true);
			bodyTypeRepository.save(bodyType);
			return true;
		}else {
			return false;
		}
	}
}
