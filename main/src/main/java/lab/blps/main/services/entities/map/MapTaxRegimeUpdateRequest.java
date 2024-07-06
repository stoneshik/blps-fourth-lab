package lab.blps.main.services.entities.map;

import lab.blps.main.bd.entites.enums.TaxFeatureEnum;
import lab.blps.main.bd.entites.enums.TaxpayerCategoryEnum;
import lab.blps.main.dto.TaxRegimeUpdateRequestDto;
import lab.blps.main.services.entities.TaxRegimeUpdateRequest;

import java.util.ArrayList;
import java.util.List;

public class MapTaxRegimeUpdateRequest {
    public static TaxRegimeUpdateRequest mapFromDto(TaxRegimeUpdateRequestDto taxRegimeUpdateRequestDto) {
        TaxRegimeUpdateRequest taxRegimeUpdateRequest = new TaxRegimeUpdateRequest();
        taxRegimeUpdateRequest.setId(taxRegimeUpdateRequestDto.getId());
        List<TaxpayerCategoryEnum> taxpayerCategories = new ArrayList<>();
        for (String taxpayerCategory : taxRegimeUpdateRequestDto.getTaxpayerCategories()) {
            taxpayerCategories.add(TaxpayerCategoryEnum.valueOf(taxpayerCategory.toUpperCase()));
        }
        taxRegimeUpdateRequest.setTaxpayerCategories(taxpayerCategories);
        List<TaxFeatureEnum> taxFeatures = new ArrayList<>();
        for (String taxFeature : taxRegimeUpdateRequestDto.getTaxFeatures()) {
            taxFeatures.add(TaxFeatureEnum.valueOf(taxFeature.toUpperCase()));
        }
        taxRegimeUpdateRequest.setTaxFeatures(taxFeatures);
        taxRegimeUpdateRequest.setTitle(taxRegimeUpdateRequestDto.getTitle());
        taxRegimeUpdateRequest.setDescription(taxRegimeUpdateRequestDto.getDescription());
        taxRegimeUpdateRequest.setMaxAnnualIncomeThousands(taxRegimeUpdateRequestDto.getMaxAnnualIncomeThousands());
        taxRegimeUpdateRequest.setMaxNumberEmployees(taxRegimeUpdateRequestDto.getMaxNumberEmployees());
        return taxRegimeUpdateRequest;
    }
}
