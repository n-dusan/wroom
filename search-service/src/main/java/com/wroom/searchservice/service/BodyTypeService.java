package com.wroom.searchservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wroom.searchservice.domain.BodyType;
import com.wroom.searchservice.domain.dto.FeatureDTO;
import com.wroom.searchservice.exceptions.InvalidDataException;
import com.wroom.searchservice.repository.BodyTypeRepository;

@Service
public class BodyTypeService {
	

	private final BodyTypeRepository bodyTypeRepository;

	public BodyTypeService(BodyTypeRepository bodyTypeRepository) {
		this.bodyTypeRepository = bodyTypeRepository;
	}
	
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
		BodyType entity = this.bodyTypeRepository.findByName(bodyType.getName());
		if(entity == null) {
			return bodyTypeRepository.save(bodyType);
		}
		else {
			return null;
		}
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
