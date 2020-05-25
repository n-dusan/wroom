package xwsagent.wroomagent.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xwsagent.wroomagent.domain.GearboxType;
import xwsagent.wroomagent.domain.dto.FeatureDTO;
import xwsagent.wroomagent.exception.InvalidDataException;
import xwsagent.wroomagent.repository.GearboxTypeRepository;

@Service
public class GearboxTypeService {

	@Autowired
	GearboxTypeRepository gearboxTypeRepository;
	
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
	
	public void delete(String name) {
		GearboxType gearboxType = findByName(name);
		gearboxType.setDeleted(true);
		gearboxTypeRepository.save(gearboxType);
			
	}
	
	public GearboxType update(GearboxType gt, FeatureDTO featureDTO) {
		if(featureDTO == null) {
			throw new InvalidDataException("Forwarded DTO is null");
		}	
		
		gt.setName(featureDTO.getName());
		this.gearboxTypeRepository.save(gt);
		return gt;
	}
}
