package xwsagent.wroomagent.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LocationDTO {

    private Long id;

    @NotBlank(message = "cannot be empty")
    private String country;

    @NotBlank(message = "cannot be empty")
    private String city;

}
