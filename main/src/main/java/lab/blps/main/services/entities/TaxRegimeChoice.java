package lab.blps.main.services.entities;

import java.util.List;

import lab.blps.main.bd.entites.enums.TaxFeatureEnum;
import lab.blps.main.bd.entites.enums.TaxpayerCategoryEnum;
import lombok.Data;

@Data
public class TaxRegimeChoice {
    private List<TaxpayerCategoryEnum> taxpayerCategories;
    private List<TaxFeatureEnum> taxFeatures;
    private Long maxAnnualIncomeThousands;
    private Long maxNumberEmployees;

    public TaxRegimeChoice() {}

    public TaxRegimeChoice(
            List<TaxpayerCategoryEnum> taxpayerCategories,
            List<TaxFeatureEnum> taxFeatures,
            Long maxAnnualIncomeThousands,
            Long maxNumberEmployees
    ) {
        this.taxpayerCategories = taxpayerCategories;
        this.taxFeatures = taxFeatures;
        this.maxAnnualIncomeThousands = maxAnnualIncomeThousands;
        this.maxNumberEmployees = maxNumberEmployees;
    }
}
