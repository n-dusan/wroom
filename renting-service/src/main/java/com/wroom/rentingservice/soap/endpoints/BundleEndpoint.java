package com.wroom.rentingservice.soap.endpoints;

import com.wroom.rentingservice.domain.BundledRequests;
import com.wroom.rentingservice.repository.BundleRepository;
import com.wroom.rentingservice.service.BundleService;
import com.wroom.rentingservice.soap.converters.BundledRequestsSoapConverter;
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
public class BundleEndpoint {

    @Autowired
    private BundleRepository bundleRepository;

    @Autowired
    private BundleService bundleService;

    private static final String NAMESPACE_URI ="http://ftn.com/wroom-agent/xsd";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "BundledRequestsListSoapRequest")
    @ResponsePayload
    public BundledRequestsListSoapResponse sync(@RequestPayload BundledRequestsListSoapRequest request) {
        log.info("sync=bundles action=started");

        List<BundledRequests> bundledRequests = this.bundleRepository.findBundledRequestsByVehicleOwnerEmail(request.getCompanyEmail());
        BundledRequestsListSoapResponse response = new BundledRequestsListSoapResponse();

        List<BundledRequestsSoap> bundledRequestsSoaps = BundledRequestsSoapConverter.toEntityList(
                bundledRequests, BundledRequestsSoapConverter::toSoapBundle
        );

        response.setRequests(bundledRequestsSoaps);

        log.info("sync=bundles action=completed");
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "BundleUpdateRequest")
    @ResponsePayload
    public BundleUpdateResponse updateId(@RequestPayload BundleUpdateRequest request) {
        log.info("update=bundles action=started");

        this.bundleService.updateLocalId(request.getId(), request.getLocalId());

        BundleUpdateResponse response = new BundleUpdateResponse();
        response.setId(request.getId());
        response.setLocalId(request.getLocalId());

        log.info("sync=bundles action=ended");
        return response;
    }
}
