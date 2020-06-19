package com.wroom.rentingservice.soap.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.wroom.rentingservice.domain.RentRequest;
import com.wroom.rentingservice.repository.AdRepository;
import com.wroom.rentingservice.repository.RentRequestRepository;
import com.wroom.rentingservice.soap.converters.RentRequestSoapConverter;
import com.wroom.rentingservice.soap.xsd.SendRentRequest;
import com.wroom.rentingservice.soap.xsd.SendRentResponse;

@Endpoint
public class RentRequestEndpoint {

	private static final String NAMESPACE_URI ="http://ftn.com/renting-service/xsd";
	
	@Autowired
	private RentRequestRepository rentsRepository;
	
	@Autowired 
	private AdRepository adsRepository;
	
	@Autowired
	public RentRequestEndpoint(RentRequestRepository rentsRepository,
			AdRepository adsRepository) {
		this.rentsRepository = rentsRepository;
		this.adsRepository = adsRepository;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "SendRentRequest")
	@ResponsePayload
	public SendRentResponse getCountry(@RequestPayload SendRentRequest request) {
		System.out.println(">>>>>>> Received a RentRequest");
		SendRentResponse response = new SendRentResponse();
		
		RentRequest entity = RentRequestSoapConverter.fromSoapRequest(request.getRentRequest());
		entity.setAd(this.adsRepository.findById(request.getRentRequest().getAd()).get());

		// TODO: report i bundle dodati 
//		entity.setRentReport(rentReport);
//		entity.setBundle(bundle);
		
		this.rentsRepository.save(entity);
		System.out.println(">>>>>>> Saved a RentRequest");

		response.setRentRequest(RentRequestSoapConverter.toSoapRequest(entity));
		return response;
	}
	
}
