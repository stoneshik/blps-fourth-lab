package lab.blps.main.services.entities.map;

import lab.blps.main.bd.entites.enums.TaxFeatureEnum;
import lab.blps.main.bd.entites.enums.TaxpayerCategoryEnum;
import lab.blps.main.dto.TaxRegimeChoiceDto;
import lab.blps.main.services.entities.TaxRegimeChoice;

import java.util.ArrayList;
import java.util.List;

public class MapTaxRegimeChoice {
    public static TaxRegimeChoiceDto mapToDto(TaxRegimeChoice taxRegimeChoice) {
        TaxRegimeChoiceDto taxRegimeChoiceDto = new TaxRegimeChoiceDto();
        List<String> taxpayerCategories = new ArrayList<>();
        for (TaxpayerCategoryEnum taxpayerCategoryEnum : taxRegimeChoice.getTaxpayerCategories()) {
            taxpayerCategories.add(taxpayerCategoryEnum.name().toLowerCase());
        }
        taxRegimeChoiceDto.setTaxpayerCategories(taxpayerCategories);
        List<String> taxFeatures = new ArrayList<>();
        for (TaxFeatureEnum taxFeatureEnum : taxRegimeChoice.getTaxFeatures()) {
            taxFeatures.add(taxFeatureEnum.name().toLowerCase());
        }
        taxRegimeChoiceDto.setTaxFeatures(taxFeatures);
        taxRegimeChoiceDto.setMaxAnnualIncomeThousands(taxRegimeChoice.getMaxAnnualIncomeThousands());
        taxRegimeChoiceDto.setMaxNumberEmployees(taxRegimeChoice.getMaxNumberEmployees());
        return taxRegimeChoiceDto;
    }

    public static TaxRegimeChoice mapFromDto(TaxRegimeChoiceDto taxRegimeChoiceDto) {
        TaxRegimeChoice taxRegimeChoice = new TaxRegimeChoice();
        List<TaxpayerCategoryEnum> taxpayerCategories = new ArrayList<>();
        for (String taxpayerCategory : taxRegimeChoiceDto.getTaxpayerCategories()) {
            taxpayerCategories.add(TaxpayerCategoryEnum.valueOf(taxpayerCategory.toUpperCase()));
        }
        taxRegimeChoice.setTaxpayerCategories(taxpayerCategories);
        List<TaxFeatureEnum> taxFeatures = new ArrayList<>();
        for (String taxFeature : taxRegimeChoiceDto.getTaxFeatures()) {
            taxFeatures.add(TaxFeatureEnum.valueOf(taxFeature.toUpperCase()));
        }
        taxRegimeChoice.setTaxFeatures(taxFeatures);
        taxRegimeChoice.setMaxAnnualIncomeThousands(taxRegimeChoiceDto.getMaxAnnualIncomeThousands());
        taxRegimeChoice.setMaxNumberEmployees(taxRegimeChoiceDto.getMaxNumberEmployees());
        return taxRegimeChoice;
    }
}
