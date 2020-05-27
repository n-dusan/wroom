package xwsagent.wroomagent.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import xwsagent.wroomagent.domain.Ad;
import xwsagent.wroomagent.repository.AdRepository;

@Service
@AllArgsConstructor
public class AdService {

	private final AdRepository adRepository;
	
	public List<Ad> getAll() {
        return adRepository.findAll();
    }
	
}
