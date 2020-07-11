package com.wroom.rentingservice.soap.converters;

import com.wroom.rentingservice.converter.AbstractConverter;
import com.wroom.rentingservice.domain.RentRequest;
import com.wroom.rentingservice.domain.enums.RequestStatus;
import com.wroom.rentingservice.soap.xsd.RentRequestSoap;
import com.wroom.rentingservice.soap.xsd.Status;

public class RentRequestSoapConverter extends AbstractConverter {

	public static RentRequestSoap toSoapRequest(RentRequest entity) {
		RentRequestSoap ret = new RentRequestSoap();

		ret.setId(entity.getId());
		ret.setLocalId(entity.getLocalId() == null ? null : entity.getLocalId());

		ret.setStatus(Status.fromValue(entity.getStatus().toString()));
		ret.setFromDate(entity.getFromDate());
		ret.setToDate(entity.getToDate());
		ret.setAd(entity.getAd().getLocalId());
		ret.setRentReport(entity.getRentReport() == null ? null : entity.getRentReport().getLocalId());
		ret.setBundle(entity.getBundle() == null ? null : entity.getBundle().getLocalId());
		ret.setRequestedUserId(entity.getRequestedUserId());

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
