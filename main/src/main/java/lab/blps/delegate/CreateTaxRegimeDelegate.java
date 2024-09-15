package lab.blps.delegate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import lab.blps.main.dto.TaxRegimeCreateRequestDto;
import lab.blps.main.services.CrudTaxRegimeService;
import lab.blps.main.services.entities.TaxRegimeCreateRequest;
import lab.blps.main.services.entities.map.MapTaxRegimeCreateRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateTaxRegimeDelegate implements JavaDelegate {
    private final CrudTaxRegimeService crudTaxRegimeService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
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
        List<String> taxpayerCategoriesStrings = Arrays.asList(delegateExecution.getVariable("taxpayerCategories"))
            .stream()
            .map((object) -> Objects.toString(object, null))
            .toList();
        List<String> taxFeaturesStrings = Arrays.asList(delegateExecution.getVariable("taxFeatures"))
            .stream()
            .map((obj) -> Objects.toString(obj, null))
            .toList();
        String title = (String) delegateExecution.getVariable("title");
        String description = (String) delegateExecution.getVariable("description");
        Long maxAnnualIncomeThousands = (Long) delegateExecution.getVariable("maxAnnualIncomeThousands");
        Long maxNumberEmployees = (Long) delegateExecution.getVariable("maxNumberEmployees");
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
