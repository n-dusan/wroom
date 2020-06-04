package com.wroom.searchservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wroom.searchservice.domain.BrandType;
import com.wroom.searchservice.domain.dto.FeatureDTO;
import com.wroom.searchservice.exceptions.InvalidDataException;
import com.wroom.searchservice.repository.BrandTypeRepository;

@Service
public class BrandTypeService {

	private final BrandTypeRepository brandTypeRepository;

	public BrandTypeService(BrandTypeRepository brandTypeRepository) {
		this.brandTypeRepository = brandTypeRepository;
	}
	
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
		BrandType entity = this.brandTypeRepository.findByName(brandType.getName());
		if(entity == null) {
			return brandTypeRepository.save(brandType);
		}
		else {
			return null;
		}
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
