package lab.blps.main.services.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lab.blps.main.bd.entites.TaxFeatures;
import lab.blps.main.bd.entites.TaxpayerCategories;
import lombok.Data;

import java.util.List;

@Data
public class TaxRegimeWithFeaturesAndCategory {
    @Id
    @Column(name = "id")
    private Long id;
    @NotNull
    @Column(name = "title", length = 100)
    private String title;
    @NotNull
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "taxpayer_category")
    private List<TaxpayerCategories> taxpayerCategories;
    @Column(name = "tax_feature")
    private List<TaxFeatures> taxFeatures;
    @Min(value = 100L)
    @Column(name = "max_annual_income_thousands")
    private Long maxAnnualIncomeThousands;
    @Min(value = 0L)
    @Column(name = "max_number_employees")
    private Long maxNumberEmployees;

    public TaxRegimeWithFeaturesAndCategory() {}

    public TaxRegimeWithFeaturesAndCategory(
            Long id,
            @NotNull String title,
            @NotNull String description,
            List<TaxpayerCategories> taxpayerCategories,
            List<TaxFeatures> taxFeatures,
            @Min(value = 100L) Long maxAnnualIncomeThousands,
            @Min(value = 0L) Long maxNumberEmployees
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.taxpayerCategories = taxpayerCategories;
        this.taxFeatures = taxFeatures;
        this.maxAnnualIncomeThousands = maxAnnualIncomeThousands;
        this.maxNumberEmployees = maxNumberEmployees;
    }
}
