package lab.blps.delegate;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.identity.Authentication;
import org.springframework.stereotype.Component;

import lab.blps.security.bd.entities.user.RoleEnum;
import lab.blps.security.services.UserService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GetAmountRequestDelegate implements JavaDelegate {
    private final UserService userService;
    private final IdentityService identityService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String userId = (String) delegateExecution.getVariable("userId");
        Authentication authentication = identityService.getCurrentAuthentication();
        if (!authentication.getGroupIds().contains(RoleEnum.ADMIN.name()) &&
            !authentication.getUserId().equals(userId)) {
            throw new BpmnError("unauthorized");
        }
        Integer amountRequest = userService.loadAmountRequest(userId);
        delegateExecution.setVariable("response", amountRequest);
    }
}
