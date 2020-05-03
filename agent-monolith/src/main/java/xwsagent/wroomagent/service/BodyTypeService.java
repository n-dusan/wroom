package xwsagent.wroomagent.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xwsagent.wroomagent.domain.BodyType;
import xwsagent.wroomagent.dto.BodyTypeDTO;
import xwsagent.wroomagent.repository.BodyTypeRepository;

@Service
public class BodyTypeService {
	
	@Autowired
	BodyTypeRepository bodyTypeRepository;
	
	public BodyType findByName(String name) {
		return bodyTypeRepository.findByName(name);
	}
	
	public BodyType findById(Long id){
		BodyType bt = bodyTypeRepository.findById(id).orElseGet(null);
		return bt;
	}
	
	public List<BodyType> findAll(){
		List<BodyType> list = new ArrayList<BodyType>();
		for(BodyType bodyType :  bodyTypeRepository.findAll()) {
			if(bodyType.isDeleted() == false) {
				list.add(bodyType);
			}		
		}
		return list;
	}
	
	public boolean create(BodyTypeDTO bodyTypeDTO) {
		if(findByName(bodyTypeDTO.getName()) == null) {
			BodyType bodyType = new BodyType();
			bodyType.setName(bodyTypeDTO.getName());
			bodyTypeRepository.save(bodyType);
			return true;
		}else {
			return false;
		}
	}
	
	public boolean delete(String name) {
		if(findByName(name) != null) {
			BodyType bodyType = findByName(name);
			bodyType.setDeleted(true);
			bodyTypeRepository.save(bodyType);
			return true;
		}else {
			return false;
		}
	}
	
	public boolean update(BodyType bt, BodyTypeDTO bodyTypeDTO) {
		if(bodyTypeDTO != null && (findByName(bodyTypeDTO.getName()) == null)) {
			bt.setName(bodyTypeDTO.getName());
			this.bodyTypeRepository.save(bt);
			return true;
		}else {
			return false;
		}
}
}
