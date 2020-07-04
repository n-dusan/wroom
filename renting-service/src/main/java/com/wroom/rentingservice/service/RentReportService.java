package com.wroom.rentingservice.service;

import com.wroom.rentingservice.converter.RentReportConverter;
import com.wroom.rentingservice.domain.Ad;
import com.wroom.rentingservice.domain.Debt;
import com.wroom.rentingservice.domain.RentReport;
import com.wroom.rentingservice.domain.RentRequest;
import com.wroom.rentingservice.domain.dto.RentReportDTO;
import com.wroom.rentingservice.domain.enums.DebtStatus;
import com.wroom.rentingservice.exception.GeneralException;
import com.wroom.rentingservice.repository.DebtRepository;
import com.wroom.rentingservice.repository.RentReportRepository;
import com.wroom.rentingservice.repository.RentRequestRepository;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RentReportService {

    private final RentReportRepository rentReportRepository;
    private final RentsService rentsService;
    private final RentRequestRepository rentRequestRepository;
    private final DebtRepository debtRepository;

    public RentReportService(RentReportRepository rentReportRepository, RentsService rentsService, RentRequestRepository rentRequestRepository, DebtRepository debtRepository) {
        this.rentReportRepository = rentReportRepository;
        this.rentsService = rentsService;
        this.rentRequestRepository = rentRequestRepository;
        this.debtRepository = debtRepository;
    }

    public RentReport findById(Long id) {
        return this.rentReportRepository.findById(id).orElseThrow(
                () -> new GeneralException("Unable to find reference to " + id.toString() + " rent report"));
    }

    public List<RentReport> findAll() {
        return this.rentReportRepository.findAll();
    }

    public Debt findDebtById(Long id) {
        return this.debtRepository.findById(id).orElseThrow(
                () -> new GeneralException("Unable to find reference to " + id.toString() + "debt"));
    }

    public RentReport save(RentReportDTO dto) {
        System.out.println("Zes" + dto);
        RentReport rentReport = RentReportConverter.toEntity(dto);
        //rentReport.setRentRequest(rentsService.findById(dto.getRentRequestId()));
        RentReport saved = rentReportRepository.save(rentReport);

        RentRequest rentRequest = rentsService.findById(dto.getRentRequestId());
        rentRequest.setRentReport(saved);
        rentRequestRepository.save(rentRequest);

        return saved;
    }

    public List<RentReport> getReportsForVehicle(Long vehicleId) {
        return this.rentReportRepository.findAllByVehicle(vehicleId);
    }
    
    public void addDebt(RentReportDTO report, Authentication auth){

    	RentReport reportNew = new RentReport();
    	List<RentReport> reports = findAll();
    	
    	for(RentReport rr : reports) {
    		if(rr.getNote().equals(report.getNote())) {
    			reportNew = rr;
    		}
    	}
    	
    	RentRequest r = rentRequestRepository.findByReportId(reportNew.getId());
    	//User user = r.getRequestedUser();
    	Ad ad = r.getAd();
    	if(ad.getMileLimit() < report.getTraveledMiles()) {
    		Debt debt = new Debt();
    		debt.setMiles(report.getTraveledMiles()-ad.getMileLimit());
    		debt.setPriceListId(ad.getPriceListId());
    		debt.setRentRequestId(r.getId());
    		//debt.setUser(user);
    		debt.setStatus(DebtStatus.UNPAID);
    		debtRepository.save(debt);
    	}
    }
    
    public List<Debt> getDebts(Authentication auth){
    	//User user = userService.findByEmail(((UserPrincipal) auth.getPrincipal()).getUsername());
    	List<Debt> all = this.debtRepository.findAll();
    	List<Debt> debts = new ArrayList<Debt>();
    	for(Debt d : all) {
//    		if(d.getUser() == user && d.getStatus() == DebtStatus.UNPAID) {
//    			debts.add(d);
//    		}
    	}
    	return debts;
    }
    
    public Debt pay(Long id) {
    	Debt debt = findDebtById(id);
    	debt.setStatus(DebtStatus.PAID);
    	return debtRepository.save(debt);
    }
    
    
}
