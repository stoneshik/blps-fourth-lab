package lab.blps.delegate;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import lab.blps.main.services.CrudTaxRegimeService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeleteTaxRegimeDelegate implements JavaDelegate {
    private final CrudTaxRegimeService crudTaxRegimeService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Long taxRegimeId = (Long) delegateExecution.getVariable("id");
        checkTaxRegimeId(taxRegimeId);
        crudTaxRegimeService.delete(taxRegimeId);
        delegateExecution.setVariable("response", "Информация о налоговом режиме успешно удалена!");
    }

    private void checkTaxRegimeId(Long id) {
        if (crudTaxRegimeService.getById(id) == null) {
            throw new BpmnError("removeTaxRegimeException");
        }
    }
}
