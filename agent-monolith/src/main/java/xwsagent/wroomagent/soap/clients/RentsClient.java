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
import xwsagent.wroomagent.soap.converters.RentReportSoapConverter;
import xwsagent.wroomagent.soap.converters.RentRequestSoapConverter;
import xwsagent.wroomagent.soap.xsd.*;

import java.util.*;

@Log4j2
public class RentsClient extends WebServiceGatewaySupport {


	public static final String MONOLITH_USER_EMAIL = "zika@maildrop.cc";


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

	public RentReportSoapRequestResponse sendReport(RentReport entity) {
		log.info("send=rent_report action=started");
		RentReportSoapRequestResponse request = new RentReportSoapRequestResponse();
		request.setRentReport(RentReportSoapConverter.toSoap(entity));


		RentRequest rentRequest = rentRequestRepository.findByRentReportId(entity.getId());
		request.setRequestLocalId(rentRequest.getId());

		RentReportSoapRequestResponse response = (RentReportSoapRequestResponse) getWebServiceTemplate().marshalSendAndReceive(request);
		log.info("send=rent_report action=ended");
		return response;
	}


	public void syncBundles() {
		log.info("sync=bundles action=started");
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
					RentReport report = rentReportRepository.findById(bundledRequestsSoapRequest.getRentReport()).orElse(null);
					if(report != null) {
						rentRequest.setRentReport(rentReportService.findById(bundledRequestsSoapRequest.getRentReport()));
					}
				}

				rentRequest.setRequestedUser(userService.findById(bundledRequestsSoapRequest.getRequestedUserId()));

				RentRequest savedRequest = this.rentRequestRepository.save(rentRequest);

				requestsToSaveToBundle.add(savedRequest);

				if(bundledRequestsSoapRequest.getLocalId() == null) {
					//update wroom
					System.out.println("Time to update wroom bundle request");
					updateRequestId(bundledRequestsSoapRequest.getId(), savedRequest.getId());
				}
			}

			saved.setRequests(requestsToSaveToBundle);
			saved = this.bundleRepository.save(bundledRequests);
			log.info("sync=bundles action=ended");
		}


	}

	public void syncReports() {
		log.info("sync=rent_report action=started");

		RentReportListSoapRequest request = new RentReportListSoapRequest();
		request.setCompanyEmail(MONOLITH_USER_EMAIL);

		RentReportListSoapResponse response = (RentReportListSoapResponse) getWebServiceTemplate().marshalSendAndReceive(request);

		List<RentReportSoap> reportSoaps = response.getRentReport();

		for (RentReportSoap reportSoap : reportSoaps) {

			if(reportSoap.getLocalId() != null) {
				RentReport report = rentReportService.findById(reportSoap.getLocalId());
				report.setDateReport(reportSoap.getDate());
				report.setNote(reportSoap.getNote());
				report.setTraveledMiles(reportSoap.getTraveledMiles());
				this.rentReportRepository.save(report);
				continue;
			}
			RentReport newReport = RentReportSoapConverter.fromSoap(reportSoap);

			newReport = rentReportRepository.save(newReport);
			System.out.println("Time to update wroom rent report");
			updateReportId(newReport.getId(), reportSoap.getId());
		}

		log.info("sync=rent_report action=ended");
	}


	public void syncRents() {
		log.info("sync=rent_requests action=started");

		SendRentListRequest request = new SendRentListRequest();
		request.setCompanyEmail(MONOLITH_USER_EMAIL);

		SendRentListResponse response = (SendRentListResponse) getWebServiceTemplate().marshalSendAndReceive(request);
		
		List<RentRequestSoap> soapedResponse = response.getRentRequest();

		for (RentRequestSoap rentRequestSoap : soapedResponse) {

			RentRequest rentRequest = RentRequestSoapConverter.fromSoapRequest(rentRequestSoap);

			rentRequest.setAd(adService.findById(rentRequestSoap.getAd()));
			if(rentRequestSoap.getBundle() != null) {
				rentRequest.setBundle(bundleService.findById(rentRequestSoap.getBundle()));
				//continue;
			}
			if(rentRequestSoap.getRentReport() != null) {
				rentRequest.setRentReport(rentReportService.findById(rentRequestSoap.getRentReport()));
			}
			rentRequest.setRequestedUser(userService.findById(rentRequestSoap.getRequestedUserId()));

			RentRequest savedRequest = this.rentRequestRepository.save(rentRequest);

			if(rentRequestSoap.getLocalId() == null) {
				//update wroom
				System.out.println("Time to update wroom rent request");
				updateRequestId(rentRequestSoap.getId(), savedRequest.getId());
			}

		}
		
		log.info("sync=rent_requests action=ended");
	}


	public void updateBundleId(Long id, Long localId) {
		log.info("Updating microservice entry with " + id + " local: " + localId);
		BundleUpdateRequest request = new BundleUpdateRequest();
		request.setId(id);
		request.setLocalId(localId);

		BundleUpdateResponse response = (BundleUpdateResponse) getWebServiceTemplate().marshalSendAndReceive(request);
		log.info("Updated a bundle with " + response.getId() + "id and  " + response.getLocalId() + " local id");
	}

	public void updateRequestId(Long id, Long localId) {
		log.info("Updating microservice entry with " + id + " local: " + localId);
		RentRequestUpdateRequest request = new RentRequestUpdateRequest();
		request.setId(id);
		request.setLocalId(localId);

		RentRequestUpdateResponse response = (RentRequestUpdateResponse) getWebServiceTemplate().marshalSendAndReceive(request);
		log.info("Updated a request with " + response.getId() + "id and  " + response.getLocalId() + " local id");
	}


	public void updateReportId(Long id, Long localId) {
		log.info("Updating microservice entry with " + id + " local: " + localId);
		RentReportUpdateRequestResponse request = new RentReportUpdateRequestResponse();
		request.setId(id);
		request.setLocalId(localId);

		RentReportUpdateRequestResponse response = (RentReportUpdateRequestResponse) getWebServiceTemplate().marshalSendAndReceive(request);
		log.info("Updated a report with " + response.getId() + "id and  " + response.getLocalId() + " local id");
	}

}
