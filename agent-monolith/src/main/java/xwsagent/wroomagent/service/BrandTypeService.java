package xwsagent.wroomagent.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xwsagent.wroomagent.domain.BrandType;
import xwsagent.wroomagent.dto.BrandTypeDTO;
import xwsagent.wroomagent.repository.BrandTypeRepository;

@Service
public class BrandTypeService {

	@Autowired
	BrandTypeRepository brandTypeRepository;
	
	public BrandType findByName(String name) {
		return brandTypeRepository.findByName(name);
	}
	
	public BrandType findById(Long id){
		BrandType bt = brandTypeRepository.findById(id).orElseGet(null);
		return bt;
	}
	
	public List<BrandType> findAll(){
		List<BrandType> list = new ArrayList<BrandType>();
		for(BrandType brandType : brandTypeRepository.findAll()) {
			if(brandType.isDeleted() == false) {
				list.add(brandType);
			}		
		}
		return list;
	}
	
	public boolean create(BrandTypeDTO brandTypeDTO) {
		if(findByName(brandTypeDTO.getName()) == null) {
			BrandType brandType = new BrandType();
			brandType.setName(brandTypeDTO.getName());
			brandTypeRepository.save(brandType);
			return true;
		}else {
			return false;
		}
	}
	
	public boolean delete(String name) {
		if(findByName(name) != null) {
			BrandType brandType = findByName(name);
			brandType.setDeleted(true);
			brandTypeRepository.save(brandType);
			return true;
		}else {
			return false;
		}
	}
	
	public boolean update(BrandType bt, BrandTypeDTO brandTypeDTO) {
		if(brandTypeDTO != null && (findByName(brandTypeDTO.getName()) == null)) {
			bt.setName(brandTypeDTO.getName());
			this.brandTypeRepository.save(bt);
			return true;
		}else {
			return false;
		}
	
	}
}
