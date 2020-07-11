package com.wroom.rentingservice.soap.converters;

import java.util.ArrayList;
import java.util.List;

import com.wroom.rentingservice.converter.AbstractConverter;
import com.wroom.rentingservice.domain.BundledRequests;
import com.wroom.rentingservice.domain.RentRequest;
import com.wroom.rentingservice.soap.xsd.BundledRequestsSoap;
import com.wroom.rentingservice.soap.xsd.RentRequestSoap;

public class BundledRequestsSoapConverter extends AbstractConverter {

	public static BundledRequestsSoap toSoapBundle(BundledRequests entity) {
		BundledRequestsSoap ret = new BundledRequestsSoap();

		ret.setId(entity.getId());
		ret.setLocalId(entity.getLocalId() == null ? null : entity.getLocalId());

		List<RentRequest> list = new ArrayList<RentRequest>(entity.getRequests());
		for(RentRequest r: list) {
			ret.getRequests().add(RentRequestSoapConverter.toSoapRequest(r));
		}
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
