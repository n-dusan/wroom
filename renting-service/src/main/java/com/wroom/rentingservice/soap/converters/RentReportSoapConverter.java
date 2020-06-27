package com.wroom.rentingservice.soap.converters;

import com.wroom.rentingservice.converter.AbstractConverter;
import com.wroom.rentingservice.domain.RentReport;
import com.wroom.rentingservice.soap.xsd.RentReportSoap;


public class RentReportSoapConverter extends AbstractConverter  {

    public static RentReportSoap toSoap(RentReport entity) {
        RentReportSoap ret = new RentReportSoap();

        ret.setId(entity.getId());
        ret.setLocalId(entity.getLocalId() == null ? null : entity.getLocalId());
        ret.setDate(entity.getDate());
        ret.setNote(entity.getNote());
        ret.setTraveledMiles(entity.getTraveledMiles());

        return ret;
    }

    public static RentReport fromSoap(RentReportSoap soap) {
        RentReport ret = new RentReport();

        ret.setLocalId(soap.getLocalId());
        ret.setTraveledMiles(soap.getTraveledMiles());
        ret.setNote(soap.getNote());
        ret.setDate(soap.getDate());

        return ret;
    }
}
