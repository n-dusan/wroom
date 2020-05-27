package xwsagent.wroomagent.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xwsagent.wroomagent.config.EndpointConfig;
import xwsagent.wroomagent.converter.AdConverter;
import xwsagent.wroomagent.domain.dto.AdDTO;
import xwsagent.wroomagent.service.AdService;

@RestController
@RequestMapping(value = EndpointConfig.AD_BASE_URL)
//@Log4j2
public class AdController {

	private final AdService adService;

    public AdController(AdService adService) {
        this.adService = adService;
    }
	
	/**
     * GET /api/ad
     * @return all ads
     */
    @GetMapping
    public ResponseEntity<List<AdDTO>> getAll() {
        return new ResponseEntity<>(
                AdConverter.fromEntityList(adService.getAll(), AdConverter::fromEntity),
                HttpStatus.OK
        );
    }
	
}
