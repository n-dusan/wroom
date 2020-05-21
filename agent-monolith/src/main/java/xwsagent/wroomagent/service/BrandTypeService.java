package xwsagent.wroomagent.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xwsagent.wroomagent.domain.BrandType;
import xwsagent.wroomagent.domain.dto.FeatureDTO;
import xwsagent.wroomagent.exception.InvalidDataException;
import xwsagent.wroomagent.repository.BrandTypeRepository;

@Service
public class BrandTypeService {

	@Autowired
	BrandTypeRepository brandTypeRepository;
	
	public List<BrandType> getAll() {
		List<BrandType> ret = new ArrayList<BrandType>();
        for(BrandType bt : brandTypeRepository.findAll()) {
        	if(bt.isDeleted() == false) {
        		ret.add(bt);
        	}
        }
        return ret;
    }
	
	public BrandType save(BrandType brandType) {
	    return brandTypeRepository.save(brandType);
	}
	
	public BrandType findByName(String name) {
		return brandTypeRepository.findByName(name);
	}
	
	public BrandType findById(Long id){
		BrandType bt = brandTypeRepository.findById(id).orElseGet(null);
		return bt;
	}
	
	public void delete(String name) {
		BrandType brandType = findByName(name);
		brandType.setDeleted(true);
		brandTypeRepository.save(brandType);
			
	}
	
	public BrandType update(BrandType bt, FeatureDTO featureDTO) {
		if(featureDTO == null) {
			throw new InvalidDataException("Forwarded DTO is null");
		}
		
		bt.setName(featureDTO.getName());
		this.brandTypeRepository.save(bt);
		return bt;
	}
}
