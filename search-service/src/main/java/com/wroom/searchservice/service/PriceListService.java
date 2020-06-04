package com.wroom.searchservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wroom.searchservice.domain.PriceList;
import com.wroom.searchservice.domain.dto.PriceListDTO;
import com.wroom.searchservice.exceptions.GeneralException;
import com.wroom.searchservice.repository.PriceListRepository;

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

//	public List<PriceList> getUserAll(Long user_id) {
//		return priceListRepository.findAllActiveUser(user_id);
//	}

	public PriceList findById(Long id) {
		return priceListRepository.findById(id).orElseThrow(
				() -> new GeneralException("Unable to find reference to " + id.toString() + " price list"));
	}

	public PriceList save(PriceList priceList) {
		PriceList entity = priceListRepository.save(priceList);
		return entity;
	}

	public PriceList update(Long id, PriceListDTO dto) {
		if (dto == null) {
			throw new GeneralException("Forwarded DTO is null");
		}
		PriceList priceList = findById(id);
		priceList.setPriceCDW(dto.getPriceCDW());
		priceList.setDiscount(dto.getDiscount());
		priceList.setPricePerMile(dto.getPricePerMile());
		priceList.setPricePerDay(dto.getPricePerDay());
		
		PriceList entity = priceListRepository.save(priceList);
		return priceList;
	}

	public void delete(Long id) {
		PriceList priceList = findById(id);
		priceList.setDeleted(true);
		priceListRepository.save(priceList);
}

}
