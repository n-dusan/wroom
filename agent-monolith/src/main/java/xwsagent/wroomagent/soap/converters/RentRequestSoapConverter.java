package xwsagent.wroomagent.soap.converters;

import xwsagent.wroomagent.domain.RentRequest;
import xwsagent.wroomagent.domain.enums.RequestStatus;
import xwsagent.wroomagent.soap.xsd.RentRequestSoap;
import xwsagent.wroomagent.soap.xsd.Status;

public class RentRequestSoapConverter {

	public static RentRequestSoap toSoapRequest(RentRequest entity) {
		RentRequestSoap ret = new RentRequestSoap();
		ret.setId(entity.getId());
		ret.setStatus(Status.fromValue(entity.getStatus().toString()));
		ret.setFromDate(entity.getFromDate());
		ret.setToDate(entity.getToDate());
		ret.setRequestedUserUsername(entity.getRequestedUser().getEmail());
		ret.setRequestedUserId(entity.getRequestedUser() == null ? null : entity.getRequestedUser().getId());
		ret.setAd(entity.getAd().getId());
		ret.setOwnerUsername(entity.getAd().getVehicle().getOwner().getEmail());
		ret.setRentReport(entity.getRentReport() == null ? null : entity.getRentReport().getId());
		ret.setBundle(entity.getBundle() == null ? null : entity.getBundle().getId());
		return ret;
	}
	
	public static RentRequest fromSoapRequest(RentRequestSoap soap) {
		return new RentRequest(
				soap.getLocalId(),
				RequestStatus.valueOf(soap.getStatus().toString()),
				soap.getFromDate(),
				soap.getToDate(),
				null,
				null,
				null,
				null
		);
	}
	
}
