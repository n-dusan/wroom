package xwsagent.wroomagent.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.Date;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AdDTO {

    private Long id;

    @NotNull(message = "an important field")
    private Long vehicleId;

    @NotNull(message = "an important field")
    private Long priceListId;

    @NotNull(message = "an important field")
    @FutureOrPresent(message = "dates have to be in future")
    private Date availableFrom;

    @NotNull(message = "an important field")
    @Future(message = "dates have to be in future")
    private Date availableTo;

    @Min(value = 0, message = "0 < mile limit < 100000000")
    @Max(value = 100000000, message = "0 < mile limit < 100000000")
    private Double mileLimit;

    private boolean mileLimitEnabled;

    @NotNull(message = "an important field")
    private Long locationId;

    @NotNull(message = "an important field")
    @Size(max = 20, message="must be less than or equal to 20")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Only alphanumeric characters")
    private String address;

    private boolean gps;
    
    private Double averageRate; 
}
