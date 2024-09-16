package lab.blps.delegate;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import lab.blps.main.bd.entites.enums.TaxFeatureEnum;
import lab.blps.main.bd.entites.enums.TaxpayerCategoryEnum;
import lab.blps.main.dto.TaxRegimeUpdateRequestDto;
import lab.blps.main.services.CrudTaxRegimeService;
import lab.blps.main.services.entities.TaxRegimeUpdateRequest;
import lab.blps.main.services.entities.map.MapTaxRegimeUpdateRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UpdateTaxRegimeDelegate implements JavaDelegate {
    private final CrudTaxRegimeService crudTaxRegimeService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        TaxRegimeUpdateRequestDto taxRegimeUpdateRequestDto = createTaxRegimeUpdateRequestDto(delegateExecution);
        TaxRegimeUpdateRequest taxRegimeUpdateRequest;
        try {
            taxRegimeUpdateRequest = MapTaxRegimeUpdateRequest.mapFromDto(taxRegimeUpdateRequestDto);
        } catch (Exception e) {
            throw new BpmnError("updateTaxRegimeException");
        }
        checkTaxRegimeId(taxRegimeUpdateRequest.getId());
        crudTaxRegimeService.update(taxRegimeUpdateRequest);
        delegateExecution.setVariable("response", "Информация об налоговом режиме успешно обновлена!");
    }

    private TaxRegimeUpdateRequestDto createTaxRegimeUpdateRequestDto(DelegateExecution delegateExecution) {
        Long taxRegimeId = ((Number) delegateExecution.getVariable("id")).longValue();
        List<String> taxpayerCategoriesStrings = new ArrayList<>();
        List<String> taxFeaturesStrings = new ArrayList<>();
        boolean individualEntrepreneur = (boolean) delegateExecution.getVariable("individualEntrepreneur");
        boolean legalEntity = (boolean) delegateExecution.getVariable("legalEntity");
        boolean individual = (boolean) delegateExecution.getVariable("individual");
        if (individualEntrepreneur) {
            taxpayerCategoriesStrings.add(
                TaxpayerCategoryEnum.INDIVIDUAL_ENTREPRENEUR.name()
            );
        }
        if (legalEntity) {
            taxpayerCategoriesStrings.add(
                TaxpayerCategoryEnum.LEGAL_ENTITY.name()
            );
        }
        if (individual) {
            taxpayerCategoriesStrings.add(
                TaxpayerCategoryEnum.INDIVIDUAL.name()
            );
        }
        boolean productionExcisableGoods = (boolean) delegateExecution.getVariable("productionExcisableGoods");
        boolean noNeedKeepTaxRecords = (boolean) delegateExecution.getVariable("noNeedKeepTaxRecords");
        boolean noObligationSubmitDeclarations = (boolean) delegateExecution.getVariable("noObligationSubmitDeclarations");
        if (productionExcisableGoods) {
            taxFeaturesStrings.add(
                TaxFeatureEnum.PRODUCTION_EXCISABLE_GOODS.name()
            );
        }
        if (noNeedKeepTaxRecords) {
            taxFeaturesStrings.add(
                TaxFeatureEnum.NO_NEED_KEEP_TAX_RECORDS.name()
            );
        }
        if (noObligationSubmitDeclarations) {
            taxFeaturesStrings.add(
                TaxFeatureEnum.NO_OBLIGATION_SUBMIT_DECLARATIONS.name()
            );
        }
        String title = (String) delegateExecution.getVariable("title");
        String description = (String) delegateExecution.getVariable("description");
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
        return new TaxRegimeUpdateRequestDto(
            taxRegimeId,
            taxpayerCategoriesStrings,
            taxFeaturesStrings,
            title,
            description,
            maxAnnualIncomeThousands,
            maxNumberEmployees
        );
    }

    private void checkTaxRegimeId(Long id) {
        if (crudTaxRegimeService.getById(id) == null) {
            throw new BpmnError("updateTaxRegimeException");
        }
    }
}
