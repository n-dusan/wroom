package xwsagent.wroomagent.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AdDTO {

    private Long vehicleId;
    private Long priceListId;
    private Date availableFrom;
    private Date availableTo;
    private Double mileLimit;
    private boolean mileLimitEnabled;
    private Long locationId;
    private String address;
    private boolean gps;
}
