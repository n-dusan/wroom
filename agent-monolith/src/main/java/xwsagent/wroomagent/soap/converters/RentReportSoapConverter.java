package xwsagent.wroomagent.soap.converters;

import xwsagent.wroomagent.converter.AbstractConverter;
import xwsagent.wroomagent.domain.RentReport;
import xwsagent.wroomagent.soap.xsd.RentReportSoap;

public class RentReportSoapConverter extends AbstractConverter  {

    public static RentReportSoap toSoap(RentReport entity) {
        RentReportSoap ret = new RentReportSoap();

        ret.setLocalId(entity.getId());
        ret.setDate(entity.getDate());
        ret.setNote(entity.getNote());
        ret.setTraveledMiles(entity.getTraveledMiles());

        return ret;
    }

    public static RentReport fromSoap(RentReportSoap soap) {
        RentReport ret = new RentReport();

        ret.setId(soap.getLocalId() == null ? null : soap.getLocalId());
        ret.setTraveledMiles(soap.getTraveledMiles());
        ret.setNote(soap.getNote());
        ret.setDate(soap.getDate());

        return ret;
    }
}
