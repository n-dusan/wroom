package com.wroom.searchservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wroom.searchservice.domain.GearboxType;
import com.wroom.searchservice.domain.dto.FeatureDTO;
import com.wroom.searchservice.exceptions.InvalidDataException;
import com.wroom.searchservice.repository.GearboxTypeRepository;

@Service
public class GearboxTypeService {


	private final GearboxTypeRepository gearboxTypeRepository;

	public GearboxTypeService(GearboxTypeRepository gearboxTypeRepository) {
		this.gearboxTypeRepository = gearboxTypeRepository;
	}
	
	public List<GearboxType> getAll() {
		List<GearboxType> ret = new ArrayList<GearboxType>();
		for(GearboxType gt : gearboxTypeRepository.findAll()) {
			if(gt.isDeleted() == false) {
				ret.add(gt);
			}
		}
		return ret;
	}
	
	public GearboxType save(GearboxType gearboxType) {
		GearboxType entity = this.gearboxTypeRepository.findByName(gearboxType.getName());
		if(entity == null) {
			return gearboxTypeRepository.save(gearboxType);
		}
		else {
			return null;
		}
	    
	}
	
	public GearboxType findByName(String name) {
		return gearboxTypeRepository.findByName(name);
	}
	
	public GearboxType findById(Long id){
		GearboxType gt = gearboxTypeRepository.findById(id).orElseGet(null);
		return gt;
	}
	
	public void delete(Long id) {
		GearboxType gearboxType = findById(id);
		gearboxType.setDeleted(true);
		gearboxTypeRepository.save(gearboxType);
	}
	
	public GearboxType update(FeatureDTO featureDTO) {
		if(featureDTO == null) {
			throw new InvalidDataException("Forwarded DTO is null");
		}	
		GearboxType gt = findById(featureDTO.getId());
		gt.setName(featureDTO.getName());
		this.gearboxTypeRepository.save(gt);
		return gt;
	}
}
