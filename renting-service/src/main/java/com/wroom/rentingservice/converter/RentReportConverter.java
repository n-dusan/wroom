package com.wroom.rentingservice.converter;

import com.wroom.rentingservice.domain.RentReport;
import com.wroom.rentingservice.domain.dto.RentReportDTO;

import java.util.Calendar;

public class RentReportConverter extends AbstractConverter {

    public static RentReportDTO fromEntity(RentReport entity) {
        return new RentReportDTO(
                entity.getId(),
                entity.getTraveledMiles(),
                entity.getNote(),
                entity.getRentRequest() == null ? null : entity.getRentRequest().getId(),
                entity.getDate()
        );
    }

    public static RentReport toEntity(RentReportDTO dto) {
        RentReport rentReport = new RentReport();

        if(dto.getDate() == null) {
            rentReport.setDate(Calendar.getInstance().getTime());
        }

        if(dto.getId() != null) {
            rentReport.setId(dto.getId());
        }

        rentReport.setNote(dto.getNote());
        rentReport.setTraveledMiles(dto.getTraveledMiles());

        return rentReport;
    }
}
