package com.wroom.rentingservice.soap.endpoints;

import com.wroom.rentingservice.domain.RentReport;
import com.wroom.rentingservice.domain.RentRequest;
import com.wroom.rentingservice.repository.RentReportRepository;
import com.wroom.rentingservice.repository.RentRequestRepository;
import com.wroom.rentingservice.service.RentReportService;
import com.wroom.rentingservice.service.RentsService;
import com.wroom.rentingservice.soap.converters.MessagesConverter;
import com.wroom.rentingservice.soap.converters.RentReportSoapConverter;
import com.wroom.rentingservice.soap.xsd.*;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
@Log4j2
public class RentReportEndpoint {

    @Autowired
    private RentReportRepository rentReportRepository;

    @Autowired
    private RentReportService rentReportService;

    @Autowired
    private RentRequestRepository rentRequestRepository;

    private static final String NAMESPACE_URI ="http://ftn.com/wroom-agent/xsd";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "RentReportListSoapRequest")
    @ResponsePayload
    public RentReportListSoapResponse sync(@RequestPayload RentReportListSoapRequest request) {
        log.info("sync=rent_report action=started");

        List<RentReport> rentReportList  = this.rentReportRepository.findRentReportByVehicleOwnerEmail(request.getCompanyEmail());
        RentReportListSoapResponse response = new RentReportListSoapResponse();

        List<RentReportSoap> rentReportSoaps = RentReportSoapConverter.toEntityList(
                rentReportList, RentReportSoapConverter::toSoap
        );
        response.setRentReport(rentReportSoaps);

        log.info("sync=rent_report action=completed");
        return response;
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "RentReportUpdateRequestResponse")
    @ResponsePayload
    public RentReportUpdateRequestResponse updateId(@RequestPayload RentReportUpdateRequestResponse request) {
        log.info("update=rent_report action=started");

        this.rentReportService.updateLocalId(request.getId(), request.getLocalId());

        RentReportUpdateRequestResponse response = new RentReportUpdateRequestResponse();
        response.setId(request.getId());
        response.setLocalId(request.getLocalId());

        log.info("update=rent_report action=ended");
        return response;
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "RentReportSoapRequestResponse")
    @ResponsePayload
    public RentReportSoapRequestResponse receiveReport(@RequestPayload RentReportSoapRequestResponse request) {
        log.info("receive=rent_report action=started");

        RentReportSoapRequestResponse response = new RentReportSoapRequestResponse();

        RentRequest rentRequest = this.rentRequestRepository.findByLocalId(request.getRequestLocalId());
        RentReport report = this.rentReportRepository.save(RentReportSoapConverter.fromSoap(request.getRentReport()));
        rentRequest.setRentReport(report);
        this.rentRequestRepository.save(rentRequest);

        response.setRentReport(RentReportSoapConverter.toSoap(report));

        log.info("receive=rent_report action=ended");
        return response;
    }
}
