package xwsagent.wroomagent.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xwsagent.wroomagent.domain.FuelType;
import xwsagent.wroomagent.domain.dto.FeatureDTO;
import xwsagent.wroomagent.exception.InvalidDataException;
import xwsagent.wroomagent.repository.FuelTypeRepository;

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
	
	public void delete(String name) {
		FuelType fuelType = findByName(name);
		fuelType.setDeleted(true);
		fuelTypeRepository.save(fuelType);
			
	}
	
	public FuelType update(FuelType ft, FeatureDTO featureDTO) {
		if(featureDTO == null) {
			throw new InvalidDataException("Forwarded DTO is null");
		}
		
		ft.setName(featureDTO.getName());
		this.fuelTypeRepository.save(ft);
		return ft;
	}
}
