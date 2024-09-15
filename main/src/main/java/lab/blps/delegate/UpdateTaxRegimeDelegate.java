package lab.blps.delegate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

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
        Long taxRegimeId = (Long) delegateExecution.getVariable("id");
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
