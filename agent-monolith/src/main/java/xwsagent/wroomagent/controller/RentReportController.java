package xwsagent.wroomagent.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import xwsagent.wroomagent.config.EndpointConfig;
import xwsagent.wroomagent.converter.DebtConverter;
import xwsagent.wroomagent.converter.RentConverter;
import xwsagent.wroomagent.converter.RentReportConverter;
import xwsagent.wroomagent.domain.dto.DebtDTO;
import xwsagent.wroomagent.domain.dto.RentReportDTO;
import xwsagent.wroomagent.domain.dto.RentRequestDTO;
import xwsagent.wroomagent.service.RentReportService;

import java.util.List;

@RestController
@RequestMapping(value = EndpointConfig.REPORT_BASE_URL)
@Log4j2
public class RentReportController {

    private final RentReportService rentReportService;

    public RentReportController(RentReportService rentReportService) {
        this.rentReportService = rentReportService;
    }


    @PostMapping
    public ResponseEntity<RentReportDTO> create(@RequestBody RentReportDTO rentReportDTO) {
        System.out.println("What did i get" + rentReportDTO);
        System.out.println("note" + rentReportDTO.getNote());

        return new ResponseEntity<>(RentReportConverter.fromEntity(rentReportService.save(rentReportDTO)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RentReportDTO>> get() {
        return new ResponseEntity<>(RentReportConverter.fromEntityList(rentReportService.findAll(), RentReportConverter::fromEntity),
                HttpStatus.OK);

    }

    @GetMapping("/chart/{vehicle_id}")
    public ResponseEntity<List<RentReportDTO>> getReportsForVehicle(@PathVariable("vehicle_id") Long id) {
        return new ResponseEntity<>(RentReportConverter.fromEntityList(rentReportService.getReportsForVehicle(id), RentReportConverter::fromEntity),
                HttpStatus.OK);
    }
    
    @PostMapping("/debts")
    public ResponseEntity<?> addDebt(@RequestBody RentReportDTO report, Authentication auth){
    	rentReportService.addDebt(report, auth);
    	return new ResponseEntity<>( HttpStatus.OK);
    }
    
    @GetMapping(value = "/alldebts")
    public ResponseEntity<List<DebtDTO>> getDebts(Authentication auth) {
    	
        return new ResponseEntity<>(DebtConverter.fromEntityList(rentReportService.getDebts(auth), DebtConverter::fromEntity),
                HttpStatus.OK);

    }
    
    @PutMapping("/pay/{id}")
	public ResponseEntity<DebtDTO> payDebt(@PathVariable("id") Long id, Authentication auth) {
		
		return new ResponseEntity<>(DebtConverter.fromEntity(rentReportService.pay(id)),
				HttpStatus.OK);
	}
    
    @GetMapping(value = "/checkDebts")
    public boolean checkDebts(Authentication auth) {
    	if(rentReportService.checkDebts(auth)) {
    		return true;
    	}else {
    		return false;
    	}
    	
    }


}
