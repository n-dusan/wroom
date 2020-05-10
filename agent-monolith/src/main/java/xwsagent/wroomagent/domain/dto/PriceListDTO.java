package xwsagent.wroomagent.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class PriceListDTO {

    private Long id;
    private Double pricePerDay;
    private Double pricePerMile;
    private Double discount;
    private Double priceCDW;

}
