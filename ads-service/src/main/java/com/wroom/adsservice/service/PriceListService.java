package com.wroom.adsservice.service;

import com.wroom.adsservice.domain.PriceList;
import com.wroom.adsservice.domain.dto.PriceListDTO;
import com.wroom.adsservice.exception.GeneralException;
import com.wroom.adsservice.repository.PriceListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceListService {

    private final PriceListRepository priceListRepository;

    public PriceListService(PriceListRepository priceListRepository) {
        this.priceListRepository = priceListRepository;
    }

    public List<PriceList> getAll() {
        return priceListRepository.findAll();
    }

    public List<PriceList> getAllActive() {
        return priceListRepository.findAllActive();
    }

    public List<PriceList> getUserAll(Long user_id) {
        return priceListRepository.findAllActiveUser(user_id);
    }

    public PriceList findById(Long id) {
        return priceListRepository.findById(id)
                .orElseThrow(() -> new GeneralException("Unable to find reference to " + id.toString() + " price list"));
    }

    public PriceList save(PriceList priceList) {
        return priceListRepository.save(priceList);
    }

    public PriceList update(Long id, PriceListDTO dto) {
        if(dto == null) {
            throw new GeneralException("Forwarded DTO is null");
        }
        PriceList priceList = findById(id);
        priceList.setPriceCDW(dto.getPriceCDW());
        priceList.setDiscount(dto.getDiscount());
        priceList.setPricePerMile(dto.getPricePerMile());
        priceList.setPricePerDay(dto.getPricePerDay());
        priceListRepository.save(priceList);
        return priceList;
    }

    public void delete(Long id) {
        PriceList priceList = findById(id);
        priceList.setDeleted(true);
        priceListRepository.save(priceList);
    }

}
