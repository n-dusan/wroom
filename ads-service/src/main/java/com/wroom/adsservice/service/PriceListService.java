package com.wroom.adsservice.service;

import com.wroom.adsservice.converter.AMQPPriceListConverter;
import com.wroom.adsservice.converter.PriceListConverter;
import com.wroom.adsservice.domain.PriceList;
import com.wroom.adsservice.domain.dto.PriceListDTO;
import com.wroom.adsservice.exception.GeneralException;
import com.wroom.adsservice.producer.AdsProducer;
import com.wroom.adsservice.producer.messages.OperationEnum;
import com.wroom.adsservice.repository.PriceListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceListService {

	private final PriceListRepository priceListRepository;
	private final AdsProducer adsProducer;

	public PriceListService(PriceListRepository priceListRepository, AdsProducer adsProducer) {
		this.priceListRepository = priceListRepository;
		this.adsProducer = adsProducer;
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
		return priceListRepository.findById(id).orElseThrow(
				() -> new GeneralException("Unable to find reference to " + id.toString() + " price list"));
	}

	public PriceList save(PriceList priceList) {
		PriceList entity = priceListRepository.save(priceList);

		// Notify search-service
		PriceListDTO dto = PriceListConverter.fromEntity(entity);
		System.out.println("Price list dto " + dto);
		this.adsProducer.send(AMQPPriceListConverter.toAdsMessage(dto, OperationEnum.CREATE));

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

		// Notify search-service
		PriceListDTO pricelistDTO = PriceListConverter.fromEntity(entity);
		this.adsProducer.send(AMQPPriceListConverter.toAdsMessage(pricelistDTO, OperationEnum.UPDATE));

		return priceList;
	}

	public void delete(Long id) {
		PriceList priceList = findById(id);
		priceList.setDeleted(true);
		
		PriceList entity = priceListRepository.save(priceList);

		// Notify search-service
		PriceListDTO pricelistDTO = PriceListConverter.fromEntity(entity);
		this.adsProducer.send(AMQPPriceListConverter.toAdsMessage(pricelistDTO, OperationEnum.DELETE));
	}

}
