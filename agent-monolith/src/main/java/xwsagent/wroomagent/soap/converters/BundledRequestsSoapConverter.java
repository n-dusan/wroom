package xwsagent.wroomagent.soap.converters;

import java.util.ArrayList;
import java.util.List;

import xwsagent.wroomagent.domain.BundledRequests;
import xwsagent.wroomagent.domain.RentRequest;
import xwsagent.wroomagent.soap.xsd.BundledRequestsSoap;
import xwsagent.wroomagent.soap.xsd.RentRequestSoap;

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
		for(RentRequestSoap r : soap.getRequests()) {
			ret.getRequests().add(RentRequestSoapConverter.fromSoapRequest(r));
		}
		return ret;
	}
	
}
