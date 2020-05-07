package xwsagent.wroomagent.service;

import java.util.ArrayList;
import java.util.List;

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
	
	public FuelType findById(Long id){
		FuelType ft = fuelTypeRepository.findById(id).orElseGet(null);
		return ft;
	}
	
	public List<FuelType> findAll(){
		List<FuelType> list = new ArrayList<FuelType>();
		for(FuelType fuelType :  fuelTypeRepository.findAll()) {
			if(fuelType.isDeleted() == false) {
				list.add(fuelType);
			}		
		}
		return list;
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
	
	public boolean update(FuelType ft, FuelTypeDTO fuelTypeDTO) {
		if(fuelTypeDTO != null && (findByName(fuelTypeDTO.getName()) == null)) {
			ft.setName(fuelTypeDTO.getName());
			this.fuelTypeRepository.save(ft);
			return true;
		}else {
			return false;
		}
	}
}
