package lab.blps.main.services.entities.map;

import lab.blps.main.bd.entites.enums.TaxFeatureEnum;
import lab.blps.main.bd.entites.enums.TaxpayerCategoryEnum;
import lab.blps.main.dto.TaxRegimeCreateRequestDto;
import lab.blps.main.services.entities.TaxRegimeCreateRequest;

import java.util.ArrayList;
import java.util.List;

public class MapTaxRegimeCreateRequest {
    public static TaxRegimeCreateRequest mapFromDto(TaxRegimeCreateRequestDto taxRegimeCreateRequestDto) {
        TaxRegimeCreateRequest taxRegimeCreateRequest = new TaxRegimeCreateRequest();
        List<TaxpayerCategoryEnum> taxpayerCategories = new ArrayList<>();
        for (String taxpayerCategory : taxRegimeCreateRequestDto.getTaxpayerCategories()) {
            taxpayerCategories.add(TaxpayerCategoryEnum.valueOf(taxpayerCategory.toUpperCase()));
        }
        taxRegimeCreateRequest.setTaxpayerCategories(taxpayerCategories);
        List<TaxFeatureEnum> taxFeatures = new ArrayList<>();
        for (String taxFeature : taxRegimeCreateRequestDto.getTaxFeatures()) {
            taxFeatures.add(TaxFeatureEnum.valueOf(taxFeature.toUpperCase()));
        }
        taxRegimeCreateRequest.setTaxFeatures(taxFeatures);
        taxRegimeCreateRequest.setTitle(taxRegimeCreateRequestDto.getTitle());
        taxRegimeCreateRequest.setDescription(taxRegimeCreateRequestDto.getDescription());
        taxRegimeCreateRequest.setMaxAnnualIncomeThousands(taxRegimeCreateRequestDto.getMaxAnnualIncomeThousands());
        taxRegimeCreateRequest.setMaxNumberEmployees(taxRegimeCreateRequestDto.getMaxNumberEmployees());
        return taxRegimeCreateRequest;
    }
}
