package lab.blps.delegate;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.identity.Authentication;
import org.springframework.stereotype.Component;

import lab.blps.main.services.CrudTaxRegimeService;
import lab.blps.security.bd.entities.user.RoleEnum;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeleteTaxRegimeDelegate implements JavaDelegate {
    private final CrudTaxRegimeService crudTaxRegimeService;
    private final IdentityService identityService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Authentication authentication = identityService.getCurrentAuthentication();
        if (!authentication.getGroupIds().contains(RoleEnum.ADMIN.name())) {
            throw new BpmnError("unauthorized");
        }
        Long taxRegimeId = ((Number) delegateExecution.getVariable("id")).longValue();
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
