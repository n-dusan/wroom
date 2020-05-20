package xwsagent.wroomagent.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xwsagent.wroomagent.converter.VehicleConverter;
import xwsagent.wroomagent.domain.Image;
import xwsagent.wroomagent.domain.Vehicle;
import xwsagent.wroomagent.domain.dto.VehicleDTO;
import xwsagent.wroomagent.repository.VehicleRepository;

@Service
public class VehicleService {

	@Autowired
	VehicleRepository vehicleRepository;
	
	public List<Vehicle> getAll(){
		return vehicleRepository.findAll();	
	}
	
	public Vehicle findOne(Long id) {
		return vehicleRepository.findById(id).orElseGet(null);
	}
	
	public Vehicle save(VehicleDTO vehicledto) {
		Vehicle entity = VehicleConverter.toEntity(vehicledto);
		System.out.println(vehicledto.getFuelType() + "" + vehicledto.getImages() + ""+ "Ovo otprilike");
		return vehicleRepository.save(entity);
	}
}
