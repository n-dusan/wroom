package xwsagent.wroomagent.service;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import xwsagent.wroomagent.converter.RentReportConverter;
import xwsagent.wroomagent.domain.Ad;
import xwsagent.wroomagent.domain.Debt;
import xwsagent.wroomagent.domain.RentReport;
import xwsagent.wroomagent.domain.RentRequest;
import xwsagent.wroomagent.domain.auth.User;
import xwsagent.wroomagent.domain.dto.RentReportDTO;
import xwsagent.wroomagent.domain.enums.DebtStatus;
import xwsagent.wroomagent.exception.InvalidReferenceException;
import xwsagent.wroomagent.jwt.UserPrincipal;
import xwsagent.wroomagent.repository.DebtRepository;
import xwsagent.wroomagent.repository.RentReportRepository;
import xwsagent.wroomagent.repository.RentRequestRepository;
import xwsagent.wroomagent.soap.clients.RentsClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class RentReportService {

    private final RentReportRepository rentReportRepository;
    private final RentsService rentsService;
    private final RentRequestRepository rentRequestRepository;
    private final RentsClient rentsClient;
    private final UserService userService;
    private final DebtRepository debtRepository;

    public RentReportService(RentReportRepository rentReportRepository, RentsService rentsService, RentRequestRepository rentRequestRepository,
                             RentsClient rentsClient, UserService userService,
    						 DebtRepository debtRepository) {
        this.rentReportRepository = rentReportRepository;
        this.rentsService = rentsService;
        this.rentRequestRepository = rentRequestRepository;
        this.rentsClient = rentsClient;
        this.userService = userService;
        this.debtRepository = debtRepository;
   

                             }

    public RentReport findById(Long id) {
        return this.rentReportRepository.findById(id).orElseThrow(
                () -> new InvalidReferenceException("Unable to find reference to " + id.toString() + " rent report"));
    }
    
    public Debt findDebtById(Long id) {
        return this.debtRepository.findById(id).orElseThrow(
                () -> new InvalidReferenceException("Unable to find reference to " + id.toString() + " debt"));
    }

    public List<RentReport> findAll() {
        return this.rentReportRepository.findAll();
    }



    public RentReport save(RentReportDTO dto) {
        RentReport rentReport = RentReportConverter.toEntity(dto);
        //rentReport.setRentRequest(rentsService.findById(dto.getRentRequestId()));
        RentReport saved = rentReportRepository.save(rentReport);

        RentRequest rentRequest = rentsService.findById(dto.getRentRequestId());
        rentRequest.setRentReport(saved);
        rentRequestRepository.save(rentRequest);
        this.rentsClient.sendReport(saved);
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
    	User user = r.getRequestedUser();
    	Ad ad = r.getAd();
    	if(ad.getMileLimit() < report.getTraveledMiles()) {
    		Debt debt = new Debt();
    		debt.setMiles(report.getTraveledMiles()-ad.getMileLimit());
    		debt.setPriceListId(ad.getPriceList().getId());
    		debt.setRentRequestId(r.getId());
    		debt.setUser(user);
    		debt.setStatus(DebtStatus.UNPAID);
    		debtRepository.save(debt);
    	}
    }
    
    public List<Debt> getDebts(Authentication auth){
    	User user = userService.findByEmail(((UserPrincipal) auth.getPrincipal()).getUsername());
    	List<Debt> all = this.debtRepository.findAll();
    	List<Debt> debts = new ArrayList<Debt>();
    	for(Debt d : all) {
    		if(d.getUser() == user && d.getStatus() == DebtStatus.UNPAID) {
    			debts.add(d);
    		}
    	}
    	return debts;
    }
    
    public Debt pay(Long id) {
    	Debt debt = findDebtById(id);
    	debt.setStatus(DebtStatus.PAID);
    	return debtRepository.save(debt);
    }
    
    public boolean checkDebts(Authentication auth) {
    	User user = userService.findByEmail(((UserPrincipal) auth.getPrincipal()).getUsername());
    	if(user.getDebts() != null) {
    		return true;
    	}else {
    		return false;
    	}
    }
}
