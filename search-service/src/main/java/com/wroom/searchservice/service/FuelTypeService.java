package com.wroom.searchservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wroom.searchservice.domain.FuelType;
import com.wroom.searchservice.domain.dto.FeatureDTO;
import com.wroom.searchservice.exceptions.InvalidDataException;
import com.wroom.searchservice.repository.FuelTypeRepository;

@Service
public class FuelTypeService {


	private final FuelTypeRepository fuelTypeRepository;

	public FuelTypeService(FuelTypeRepository fuelTypeRepository) {
		this.fuelTypeRepository = fuelTypeRepository;
	}
	
	public List<FuelType> getAll() {
		List<FuelType> ret = new ArrayList<FuelType>();
        for(FuelType ft : fuelTypeRepository.findAll()) {
        	if(ft.isDeleted() == false) {
        		ret.add(ft);
        	}
        }
        return ret;
    }
	
	public FuelType save(FuelType fuelType) {
		FuelType entity = this.fuelTypeRepository.findByName(fuelType.getName());
		if(entity == null) {
			return fuelTypeRepository.save(fuelType);
		}
		else {
			return null;
		}
	}
	
	public FuelType findByName(String name) {
		return fuelTypeRepository.findByName(name);
	}
	
	public FuelType findById(Long id){
		FuelType ft = fuelTypeRepository.findById(id).orElseGet(null);
		return ft;
	}
	
	public void delete(Long id) {
		FuelType fuelType = findById(id);
		fuelType.setDeleted(true);
		fuelTypeRepository.save(fuelType);
	}
	
	public FuelType update(FeatureDTO featureDTO) {
		if(featureDTO == null) {
			throw new InvalidDataException("Forwarded DTO is null");
		}
		FuelType ft = findById(featureDTO.getId());
		ft.setName(featureDTO.getName());
		this.fuelTypeRepository.save(ft);
		return ft;
	}
}
