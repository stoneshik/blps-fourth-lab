package lab.blps.delegate;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.identity.Authentication;
import org.springframework.stereotype.Component;

import lab.blps.main.bd.entites.enums.TaxFeatureEnum;
import lab.blps.main.bd.entites.enums.TaxpayerCategoryEnum;
import lab.blps.main.dto.TaxRegimeCreateRequestDto;
import lab.blps.main.services.CrudTaxRegimeService;
import lab.blps.main.services.entities.TaxRegimeCreateRequest;
import lab.blps.main.services.entities.map.MapTaxRegimeCreateRequest;
import lab.blps.security.bd.entities.user.RoleEnum;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateTaxRegimeDelegate implements JavaDelegate {
    private final CrudTaxRegimeService crudTaxRegimeService;
    private final IdentityService identityService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Authentication authentication = identityService.getCurrentAuthentication();
        if (!authentication.getGroupIds().contains(RoleEnum.ADMIN.name())) {
            throw new BpmnError("unauthorized");
        }
        TaxRegimeCreateRequestDto taxRegimeCreateRequestDto = createTaxRegimeCreateRequestDto(delegateExecution);
        TaxRegimeCreateRequest taxRegimeCreateRequest;
        try {
            taxRegimeCreateRequest = MapTaxRegimeCreateRequest.mapFromDto(taxRegimeCreateRequestDto);
        } catch (Exception e) {
            throw new BpmnError("createTaxRegimeException");
        }
        crudTaxRegimeService.create(taxRegimeCreateRequest);
        delegateExecution.setVariable("response", "Информация о налоговом режиме успешно добавлена!");
    }

    private TaxRegimeCreateRequestDto createTaxRegimeCreateRequestDto(DelegateExecution delegateExecution) {
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
        return new TaxRegimeCreateRequestDto(
            taxpayerCategoriesStrings,
            taxFeaturesStrings,
            title,
            description,
            maxAnnualIncomeThousands,
            maxNumberEmployees
        );
    }
}
