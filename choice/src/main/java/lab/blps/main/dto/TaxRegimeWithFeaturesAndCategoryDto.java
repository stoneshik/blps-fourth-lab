package lab.blps.main.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


@Data
public class TaxRegimeWithFeaturesAndCategoryDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("taxpayerCategories")
    private List<String> taxpayerCategories;
    @JsonProperty("taxFeatures")
    private List<String> taxFeatures;
    @JsonProperty("maxAnnualIncomeThousands")
    private Long maxAnnualIncomeThousands;
    @JsonProperty("maxNumberEmployees")
    private Long maxNumberEmployees;
}
