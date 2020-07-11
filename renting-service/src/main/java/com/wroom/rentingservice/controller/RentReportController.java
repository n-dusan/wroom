package com.wroom.rentingservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wroom.rentingservice.config.EndpointConfig;
import com.wroom.rentingservice.converter.DebtConverter;
import com.wroom.rentingservice.converter.RentReportConverter;
import com.wroom.rentingservice.domain.dto.DebtDTO;
import com.wroom.rentingservice.domain.dto.RentReportDTO;
import com.wroom.rentingservice.service.RentReportService;

import lombok.extern.log4j.Log4j2;

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
    


}
