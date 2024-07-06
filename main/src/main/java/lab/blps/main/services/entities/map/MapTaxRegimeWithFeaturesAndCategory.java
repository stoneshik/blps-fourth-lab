package lab.blps.main.services.entities.map;

import lab.blps.main.bd.entites.TaxFeatures;
import lab.blps.main.bd.entites.TaxpayerCategories;
import lab.blps.main.bd.entites.enums.TaxFeatureEnum;
import lab.blps.main.bd.entites.enums.TaxpayerCategoryEnum;
import lab.blps.main.dto.TaxRegimeWithFeaturesAndCategoryDto;
import lab.blps.main.services.entities.TaxRegimeWithFeaturesAndCategory;

import java.util.ArrayList;
import java.util.List;

public class MapTaxRegimeWithFeaturesAndCategory {
    public static TaxRegimeWithFeaturesAndCategoryDto mapToDto(
            TaxRegimeWithFeaturesAndCategory taxRegimeWithFeaturesAndCategory
    ) {
       TaxRegimeWithFeaturesAndCategoryDto taxRegimeWithFeaturesAndCategoryDto =
               new TaxRegimeWithFeaturesAndCategoryDto();
       taxRegimeWithFeaturesAndCategoryDto.setId(taxRegimeWithFeaturesAndCategory.getId());
       taxRegimeWithFeaturesAndCategoryDto.setTitle(taxRegimeWithFeaturesAndCategory.getTitle());
       taxRegimeWithFeaturesAndCategoryDto.setDescription(taxRegimeWithFeaturesAndCategory.getDescription());
       List<String> taxpayerCategories = new ArrayList<>();
       for (TaxpayerCategories taxpayerCategory : taxRegimeWithFeaturesAndCategory.getTaxpayerCategories()) {
           TaxpayerCategoryEnum taxpayerCategoryEnum = taxpayerCategory.getTaxpayerCategoryEnum();
           taxpayerCategories.add(taxpayerCategoryEnum.name().toLowerCase());
       }
       taxRegimeWithFeaturesAndCategoryDto.setTaxpayerCategories(taxpayerCategories);
       List<String> taxFeatures = new ArrayList<>();
       for (TaxFeatures taxFeature : taxRegimeWithFeaturesAndCategory.getTaxFeatures()) {
           TaxFeatureEnum taxFeatureEnum = taxFeature.getTaxFeatureEnum();
           taxFeatures.add(taxFeatureEnum.name().toLowerCase());
       }
       taxRegimeWithFeaturesAndCategoryDto.setTaxFeatures(taxFeatures);
       taxRegimeWithFeaturesAndCategoryDto.setMaxAnnualIncomeThousands(
               taxRegimeWithFeaturesAndCategory.getMaxAnnualIncomeThousands()
       );
       taxRegimeWithFeaturesAndCategoryDto.setMaxNumberEmployees(
               taxRegimeWithFeaturesAndCategory.getMaxNumberEmployees()
       );
       return taxRegimeWithFeaturesAndCategoryDto;
    }
}
