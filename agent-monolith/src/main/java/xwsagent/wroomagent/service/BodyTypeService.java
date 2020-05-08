package xwsagent.wroomagent.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xwsagent.wroomagent.domain.BodyType;
import xwsagent.wroomagent.domain.dto.FeatureDTO;
import xwsagent.wroomagent.exception.InvalidDataException;
import xwsagent.wroomagent.repository.BodyTypeRepository;

@Service
public class BodyTypeService {
	
	@Autowired
	BodyTypeRepository bodyTypeRepository;
	
	public List<BodyType> getAll() {
		List<BodyType> ret = new ArrayList<BodyType>();
        for(BodyType bt : bodyTypeRepository.findAll()) {
        	if(bt.isDeleted() == false) {
        		ret.add(bt);
        	}
        }
        return ret;
    }
	
	public BodyType save(BodyType bodyType) {
	    return bodyTypeRepository.save(bodyType);
	}
	
	public BodyType findByName(String name) {
		return bodyTypeRepository.findByName(name);
	}
	
	public BodyType findById(Long id){
		BodyType bt = bodyTypeRepository.findById(id).orElseGet(null);
		return bt;
	}
	
	public void delete(String name) {
			BodyType bodyType = findByName(name);
			bodyType.setDeleted(true);
			bodyTypeRepository.save(bodyType);
			
	}
	
	public BodyType update(BodyType bt, FeatureDTO featureDTO) {
		if(featureDTO == null) {
			throw new InvalidDataException("Forwarded DTO is null");
		}
			bt.setName(featureDTO.getName());
			this.bodyTypeRepository.save(bt);
		return bt;
}
}
