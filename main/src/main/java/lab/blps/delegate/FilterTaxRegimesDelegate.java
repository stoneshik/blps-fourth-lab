package lab.blps.delegate;

import java.util.ArrayList;
import java.util.List;

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
        List<TaxpayerCategoryEnum> taxpayerCategories = new ArrayList<>();
        List<TaxFeatureEnum> taxFeatures = new ArrayList<>();
        boolean individualEntrepreneur = (boolean) delegateExecution.getVariable("individualEntrepreneur");
        boolean legalEntity = (boolean) delegateExecution.getVariable("legalEntity");
        boolean individual = (boolean) delegateExecution.getVariable("individual");
        if (individualEntrepreneur) {
            taxpayerCategories.add(
                TaxpayerCategoryEnum.INDIVIDUAL_ENTREPRENEUR
            );
        }
        if (legalEntity) {
            taxpayerCategories.add(
                TaxpayerCategoryEnum.LEGAL_ENTITY
            );
        }
        if (individual) {
            taxpayerCategories.add(
                TaxpayerCategoryEnum.INDIVIDUAL
            );
        }
        boolean productionExcisableGoods = (boolean) delegateExecution.getVariable("productionExcisableGoods");
        boolean noNeedKeepTaxRecords = (boolean) delegateExecution.getVariable("noNeedKeepTaxRecords");
        boolean noObligationSubmitDeclarations = (boolean) delegateExecution.getVariable("noObligationSubmitDeclarations");
        if (productionExcisableGoods) {
            taxFeatures.add(
                TaxFeatureEnum.PRODUCTION_EXCISABLE_GOODS
            );
        }
        if (noNeedKeepTaxRecords) {
            taxFeatures.add(
                TaxFeatureEnum.NO_NEED_KEEP_TAX_RECORDS
            );
        }
        if (noObligationSubmitDeclarations) {
            taxFeatures.add(
                TaxFeatureEnum.NO_OBLIGATION_SUBMIT_DECLARATIONS
            );
        }
        Object maxAnnualIncomeThousandsRaw = delegateExecution.getVariable("maxAnnualIncomeThousands");
        Object maxNumberEmployeesRaw = delegateExecution.getVariable("maxNumberEmployees");
        Long maxAnnualIncomeThousands = null;
        Long maxNumberEmployees = null;
        if  (maxAnnualIncomeThousandsRaw != null) {
            maxAnnualIncomeThousands = ((Number) maxAnnualIncomeThousandsRaw).longValue();
        }
        if  (maxNumberEmployeesRaw != null) {
            maxNumberEmployees = ((Number) maxNumberEmployeesRaw).longValue();
        }
        return new TaxRegimeChoice(
            taxpayerCategories,
            taxFeatures,
            maxAnnualIncomeThousands,
            maxNumberEmployees
        );
    }
}
