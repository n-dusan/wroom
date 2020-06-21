package com.wroom.rentingservice.soap.converters;

import java.util.ArrayList;
import java.util.List;

import com.wroom.rentingservice.domain.BundledRequests;
import com.wroom.rentingservice.domain.RentRequest;
import com.wroom.rentingservice.soap.xsd.BundledRequestsSoap;
import com.wroom.rentingservice.soap.xsd.RentRequestSoap;

public class BundledRequestsSoapConverter {

	public static BundledRequestsSoap toSoapBundle(BundledRequests entity) {
		BundledRequestsSoap ret = new BundledRequestsSoap();
		List<RentRequest> list = new ArrayList<RentRequest>(entity.getRequests());
		for(RentRequest r: list) {
			ret.getRequests().add(RentRequestSoapConverter.toSoapRequest(r));
		}
		ret.setId(entity.getId());
		return ret;
	}
	
	public static BundledRequests fromSoapBundle(BundledRequestsSoap soap) {
		BundledRequests ret = new BundledRequests();
		List<RentRequestSoap> requests = soap.getRequests();
		for(RentRequestSoap r : requests) {
			System.out.println(r.getId());
			ret.getRequests().add(RentRequestSoapConverter.fromSoapRequest(r));
		}
		return ret;
	}
	
}
