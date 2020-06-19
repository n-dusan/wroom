package com.wroom.rentingservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wroom.rentingservice.domain.Ad;
import com.wroom.rentingservice.exception.GeneralException;
import com.wroom.rentingservice.repository.AdRepository;

@Service
public class AdService {

    private final AdRepository adRepository;

    public AdService(AdRepository adRepository) {
        this.adRepository = adRepository;
        }

    public List<Ad> findAll() {
    	return this.adRepository.findAll();
    }

    public Ad findById(Long id) {
        return this.adRepository.findById(id).orElseThrow( 
                () -> new GeneralException("Unable to find reference to " + id.toString() + " ad"));
    }
    
}
