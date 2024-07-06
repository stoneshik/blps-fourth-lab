package lab.blps.main.services.entities;

import lab.blps.main.bd.entites.enums.TaxFeatureEnum;
import lab.blps.main.bd.entites.enums.TaxpayerCategoryEnum;
import lombok.Data;

import java.util.List;

@Data
public class TaxRegimeCreateRequest {
    private List<TaxpayerCategoryEnum> taxpayerCategories;
    private List<TaxFeatureEnum> taxFeatures;
    private String title;
    private String description;
    private Long maxAnnualIncomeThousands;
    private Long maxNumberEmployees;
}
