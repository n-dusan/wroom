package com.wroom.vehicleservice.service;

import com.wroom.vehicleservice.domain.FuelType;
import com.wroom.vehicleservice.domain.dto.FeatureDTO;
import com.wroom.vehicleservice.exception.GeneralException;
import com.wroom.vehicleservice.repository.FuelTypeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
            throw new GeneralException("Forwarded DTO is null");
        }

        ft.setName(featureDTO.getName());
        this.fuelTypeRepository.save(ft);
        return ft;
    }
}