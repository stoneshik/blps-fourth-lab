package lab.blps.main.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class TaxRegimeChoiceDto {
    @JsonProperty("taxpayerCategories")
    private List<String> taxpayerCategories;
    @JsonProperty("taxFeatures")
    private List<String> taxFeatures;
    @Min(0L)
    @JsonProperty("maxAnnualIncomeThousands")
    private Long maxAnnualIncomeThousands;
    @Min(0L)
    @JsonProperty("maxNumberEmployees")
    private Long maxNumberEmployees;
    @JsonProperty("userId")
    private String userId;
}
