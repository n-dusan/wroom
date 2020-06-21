package com.wroom.rentingservice.soap.converters;

import com.wroom.rentingservice.domain.RentRequest;
import com.wroom.rentingservice.domain.enums.RequestStatus;
import com.wroom.rentingservice.soap.xsd.RentRequestSoap;

public class RentRequestSoapConverter {

	public static RentRequestSoap toSoapRequest(RentRequest entity) {
		RentRequestSoap ret = new RentRequestSoap();
//		ret.setId(entity.getId());
//		ret.setStatus(Status.fromValue(entity.getStatus().toString()));
//		ret.setFromDate(entity.getFromDate());
//		ret.setToDate(entity.getToDate());
////		ret.setRequestedUserUsername(entity.getRequestedUserId()); 		//TODO: Skontati da li cuvati id ili username?
//		ret.setAd(entity.getAd().getId());
//		ret.setRentReport(entity.getRentReport() == null ? null : entity.getRentReport().getId());
//		ret.setBundle(entity.getBundle() == null ? null : entity.getBundle().getId());
		return ret;
	}
	
	public static RentRequest fromSoapRequest(RentRequestSoap soap) {
		RentRequest ret = new RentRequest();
		ret.setLocalId(soap.getId());
		ret.setFromDate(soap.getFromDate());
		ret.setToDate(soap.getToDate());
		ret.setStatus(RequestStatus.valueOf(soap.getStatus().toString()));
		ret.setOwnerUsername(soap.getOwnerUsername());
		return ret;
	}
	
}
