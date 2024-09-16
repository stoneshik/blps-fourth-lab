package lab.blps.delegate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lab.blps.main.bd.entites.enums.TaxFeatureEnum;
import lab.blps.main.bd.entites.enums.TaxpayerCategoryEnum;
import lab.blps.main.dto.TaxRegimeWithFeaturesAndCategoryDto;
import lab.blps.main.services.ChoiceTaxRegimeService;
import lab.blps.main.services.entities.TaxRegimeChoice;
import lab.blps.main.services.entities.TaxRegimeWithFeaturesAndCategory;
import lab.blps.main.services.entities.map.MapTaxRegimeWithFeaturesAndCategory;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FilterTaxRegimesDelegate implements JavaDelegate {
    private final ChoiceTaxRegimeService choiceTaxRegimeService;
    private final ObjectMapper objectMapper;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        TaxRegimeChoice taxRegimeChoice = createTaxRegimeChoice(delegateExecution);
        List<TaxRegimeWithFeaturesAndCategory> taxRegimes = choiceTaxRegimeService.choice(taxRegimeChoice);
        List<TaxRegimeWithFeaturesAndCategoryDto> taxRegimeDtoList = new ArrayList<>();
        for (TaxRegimeWithFeaturesAndCategory taxRegime : taxRegimes) {
            taxRegimeDtoList.add(
                MapTaxRegimeWithFeaturesAndCategory.mapToDto(
                    taxRegime
                )
            );
        }
        String responseString = objectMapper.writeValueAsString(taxRegimeDtoList);
        delegateExecution.setVariable("response", responseString);
    }

    private TaxRegimeChoice createTaxRegimeChoice(DelegateExecution delegateExecution) {
        List<String> taxpayerCategoriesStrings = Arrays.asList(delegateExecution.getVariable("taxpayerCategories"))
            .stream()
            .map((object) -> Objects.toString(object, null))
            .toList();
        List<String> taxFeaturesStrings = Arrays.asList(delegateExecution.getVariable("taxFeatures"))
            .stream()
            .map((obj) -> Objects.toString(obj, null))
            .toList();
        List<TaxpayerCategoryEnum> taxpayerCategories = taxpayerCategoriesStrings
            .stream()
            .map(TaxpayerCategoryEnum::valueOf)
            .toList();
        List<TaxFeatureEnum> taxFeatures = taxFeaturesStrings
            .stream()
            .map(TaxFeatureEnum::valueOf)
            .toList();
        Long maxAnnualIncomeThousands = ((Number) delegateExecution.getVariable("maxAnnualIncomeThousands")).longValue();
        Long maxNumberEmployees = ((Number) delegateExecution.getVariable("maxNumberEmployees")).longValue();
        return new TaxRegimeChoice(
            taxpayerCategories,
            taxFeatures,
            maxAnnualIncomeThousands,
            maxNumberEmployees
        );
    }
}
