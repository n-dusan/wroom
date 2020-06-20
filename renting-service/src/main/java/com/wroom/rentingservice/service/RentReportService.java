package com.wroom.rentingservice.service;

import com.wroom.rentingservice.converter.RentReportConverter;
import com.wroom.rentingservice.domain.RentReport;
import com.wroom.rentingservice.domain.RentRequest;
import com.wroom.rentingservice.domain.dto.RentReportDTO;
import com.wroom.rentingservice.exception.GeneralException;
import com.wroom.rentingservice.repository.RentReportRepository;
import com.wroom.rentingservice.repository.RentRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentReportService {

    private final RentReportRepository rentReportRepository;
    private final RentsService rentsService;
    private final RentRequestRepository rentRequestRepository;

    public RentReportService(RentReportRepository rentReportRepository, RentsService rentsService, RentRequestRepository rentRequestRepository) {
        this.rentReportRepository = rentReportRepository;
        this.rentsService = rentsService;
        this.rentRequestRepository = rentRequestRepository;
    }

    public RentReport findById(Long id) {
        return this.rentReportRepository.findById(id).orElseThrow(
                () -> new GeneralException("Unable to find reference to " + id.toString() + " rent report"));
    }

    public List<RentReport> findAll() {
        return this.rentReportRepository.findAll();
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
}
