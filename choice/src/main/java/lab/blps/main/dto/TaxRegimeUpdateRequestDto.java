package lab.blps.main.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class TaxRegimeUpdateRequestDto {
    @Min(1L)
    @JsonProperty("id")
    private Long id;
    @JsonProperty("taxpayerCategories")
    private List<String> taxpayerCategories;
    @JsonProperty("taxFeatures")
    private List<String> taxFeatures;
    @NotBlank
    @Size(max = 100)
    @JsonProperty("title")
    private String title;
    @NotBlank
    @JsonProperty("description")
    private String description;
    @Min(value = 100L)
    @JsonProperty("maxAnnualIncomeThousands")
    private Long maxAnnualIncomeThousands;
    @Min(value = 0L)
    @JsonProperty("maxNumberEmployees")
    private Long maxNumberEmployees;
}
