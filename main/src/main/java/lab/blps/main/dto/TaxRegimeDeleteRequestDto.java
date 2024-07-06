package lab.blps.main.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TaxRegimeDeleteRequestDto {
    @JsonProperty("id")
    private Long id;
}
