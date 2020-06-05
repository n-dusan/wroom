package xwsagent.wroomagent.service;

import org.springframework.stereotype.Service;
import xwsagent.wroomagent.domain.PriceList;
import xwsagent.wroomagent.domain.dto.PriceListDTO;
import xwsagent.wroomagent.exception.InvalidDataException;
import xwsagent.wroomagent.exception.InvalidReferenceException;
import xwsagent.wroomagent.repository.PriceListRepository;

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

    public PriceList findById(Long id) {
        return priceListRepository.findById(id)
                .orElseThrow(() -> new InvalidReferenceException("Unable to find reference to " + id.toString() + " price list"));
    }

    public PriceList save(PriceList priceList) {
        return priceListRepository.save(priceList);
    }

    public PriceList update(Long id, PriceListDTO dto) {
        if(dto == null) {
            throw new InvalidDataException("Forwarded DTO is null");
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
