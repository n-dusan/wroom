package xwsagent.wroomagent.domain.dto;

import lombok.*;

import java.util.Date;

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
