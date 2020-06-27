package xwsagent.wroomagent.service;


import org.springframework.stereotype.Service;
import xwsagent.wroomagent.converter.RentReportConverter;
import xwsagent.wroomagent.domain.RentReport;
import xwsagent.wroomagent.domain.RentRequest;
import xwsagent.wroomagent.domain.dto.RentReportDTO;
import xwsagent.wroomagent.exception.InvalidReferenceException;
import xwsagent.wroomagent.repository.RentReportRepository;
import xwsagent.wroomagent.repository.RentRequestRepository;
import xwsagent.wroomagent.soap.clients.RentsClient;

import java.util.List;

@Service
public class RentReportService {

    private final RentReportRepository rentReportRepository;
    private final RentsService rentsService;
    private final RentRequestRepository rentRequestRepository;
    private final RentsClient rentsClient;

    public RentReportService(RentReportRepository rentReportRepository, RentsService rentsService, RentRequestRepository rentRequestRepository,
                             RentsClient rentsClient) {
        this.rentReportRepository = rentReportRepository;
        this.rentsService = rentsService;
        this.rentRequestRepository = rentRequestRepository;
        this.rentsClient = rentsClient;
    }

    public RentReport findById(Long id) {
        return this.rentReportRepository.findById(id).orElseThrow(
                () -> new InvalidReferenceException("Unable to find reference to " + id.toString() + " rent report"));
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
}
