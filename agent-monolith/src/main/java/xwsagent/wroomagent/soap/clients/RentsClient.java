package xwsagent.wroomagent.soap.clients;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import xwsagent.wroomagent.domain.BundledRequests;
import xwsagent.wroomagent.domain.RentReport;
import xwsagent.wroomagent.domain.RentRequest;
import xwsagent.wroomagent.repository.BundleRepository;
import xwsagent.wroomagent.repository.RentReportRepository;
import xwsagent.wroomagent.repository.RentRequestRepository;
import xwsagent.wroomagent.service.AdService;
import xwsagent.wroomagent.service.BundleService;
import xwsagent.wroomagent.service.RentReportService;
import xwsagent.wroomagent.service.UserService;
import xwsagent.wroomagent.soap.converters.BundledRequestsSoapConverter;
import xwsagent.wroomagent.soap.converters.RentRequestSoapConverter;
import xwsagent.wroomagent.soap.xsd.*;

import java.util.*;

@Log4j2
public class RentsClient extends WebServiceGatewaySupport {


	@Autowired
	private BundleRepository bundleRepository;

	@Autowired
	private RentRequestRepository rentRequestRepository;

	@Autowired
	private AdService adService;

	@Autowired
	private BundleService bundleService;

	@Autowired
	private RentReportService rentReportService;

	@Autowired
	private UserService userService;

	@Autowired
	private RentReportRepository rentReportRepository;



	public static final String MONOLITH_USER_EMAIL = "zika@maildrop.cc";

	public SendRentResponse send(RentRequest entity, OperationRents operation) {
		SendRentRequest request = new SendRentRequest();
		request.setRentRequest(RentRequestSoapConverter.toSoapRequest(entity));
		request.setOperation(operation);
		
		System.out.println(">>>>>> Sening rent to wroom");
		SendRentResponse response = (SendRentResponse) getWebServiceTemplate().marshalSendAndReceive(request);
		System.out.println(">>>>>> Sent");
		
		return response;
	}
	
	public SendBundleResponse sendBundle(BundledRequests entity) {
		SendBundleRequest request = new SendBundleRequest();
		request.setBundle(BundledRequestsSoapConverter.toSoapBundle(entity));
		
		System.out.println(">>>>>> Sening bundle to wroom");
		SendBundleResponse response = (SendBundleResponse) getWebServiceTemplate().marshalSendAndReceive(request);
		System.out.println(">>>>>> Sent");
		
		return response;
	}


	public void syncBundles() {

		BundledRequestsListSoapRequest request = new BundledRequestsListSoapRequest();

		request.setCompanyEmail(MONOLITH_USER_EMAIL);

		BundledRequestsListSoapResponse response = (BundledRequestsListSoapResponse) getWebServiceTemplate().marshalSendAndReceive(request);

		List<BundledRequestsSoap> bundledRequestsListSoapList = response.getRequests();

		for (BundledRequestsSoap bundledRequestsSoap : bundledRequestsListSoapList) {

			BundledRequests bundledRequests = BundledRequestsSoapConverter.fromSoapBundle(bundledRequestsSoap);
			bundledRequests.getRequests().clear();

			BundledRequests saved = this.bundleRepository.save(bundledRequests);

			if(bundledRequestsSoap.getLocalId() == null) {
				//my bundle is fresh and its time to create it in database, then inform wroom of its change
				//update wroom
				System.out.println("Time to update wroom bundle");
				updateBundleId(bundledRequestsSoap.getId(), saved.getId());
			}

			Set<RentRequest> requestsToSaveToBundle = new HashSet<>();
			for (RentRequestSoap bundledRequestsSoapRequest : bundledRequestsSoap.getRequests()) {

				RentRequest rentRequest = RentRequestSoapConverter.fromSoapRequest(bundledRequestsSoapRequest);

				rentRequest.setAd(adService.findById(bundledRequestsSoapRequest.getAd()));
				rentRequest.setBundle(bundleService.findById(saved.getId()));

				if(bundledRequestsSoapRequest.getRentReport() != null) {
					rentRequest.setRentReport(rentReportService.findById(bundledRequestsSoapRequest.getRentReport()));
				}

				rentRequest.setRequestedUser(userService.findById(bundledRequestsSoapRequest.getRequestedUserId()));

				RentRequest savedRequest = this.rentRequestRepository.save(rentRequest);

				requestsToSaveToBundle.add(savedRequest);

				if(bundledRequestsSoapRequest.getLocalId() == null) {
					//update wroom
					System.out.println("Time to update wroom rent request");
					updateRequestId(bundledRequestsSoapRequest.getId(), savedRequest.getId());
				}
			}

			saved.setRequests(requestsToSaveToBundle);
			saved = this.bundleRepository.save(bundledRequests);

		}


	}


	public void updateBundleId(Long id, Long localId) {
		log.info("Updating microservice entry with " + id + " local: " + localId);
		BundleUpdateRequest request = new BundleUpdateRequest();
		request.setId(id);
		request.setLocalId(localId);

		BundleUpdateResponse response = (BundleUpdateResponse) getWebServiceTemplate().marshalSendAndReceive(request);
		log.info("Updated a comment with " + response.getId() + "id and  " + response.getLocalId() + " local id");
	}

	public void updateRequestId(Long id, Long localId) {
		log.info("Updating microservice entry with " + id + " local: " + localId);
		RentRequestUpdateRequest request = new RentRequestUpdateRequest();
		request.setId(id);
		request.setLocalId(localId);

		RentRequestUpdateResponse response = (RentRequestUpdateResponse) getWebServiceTemplate().marshalSendAndReceive(request);
		log.info("Updated a comment with " + response.getId() + "id and  " + response.getLocalId() + " local id");
	}

}
