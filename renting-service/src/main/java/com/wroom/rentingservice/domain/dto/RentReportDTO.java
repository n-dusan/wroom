package com.wroom.rentingservice.domain.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RentReportDTO {

    private Long id;
    private Double traveledMiles;
    private String note;
    private Long rentRequestId;
    private Date date;

}
