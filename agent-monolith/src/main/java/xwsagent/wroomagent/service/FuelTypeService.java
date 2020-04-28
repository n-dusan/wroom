package xwsagent.wroomagent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xwsagent.wroomagent.domain.FuelType;
import xwsagent.wroomagent.dto.FuelTypeDTO;
import xwsagent.wroomagent.repository.FuelTypeRepository;

@Service
public class FuelTypeService {

	@Autowired
	FuelTypeRepository fuelTypeRepository;
	
	public FuelType findByName(String name) {
		return fuelTypeRepository.findByName(name);
	}
	
	public boolean create(FuelTypeDTO fuelTypeDTO) {
		if(findByName(fuelTypeDTO.getName()) == null) {
			FuelType fuelType = new FuelType();
			fuelType.setName(fuelTypeDTO.getName());
			fuelTypeRepository.save(fuelType);
			return true;
		}else {
			return false;
		}
	}
	
	public boolean delete(String name) {
		if(findByName(name) != null) {
			FuelType fuelType = findByName(name);
			fuelType.setDeleted(true);
			fuelTypeRepository.save(fuelType);
			return true;
		}else {
			return false;
		}
	}
}
