package lab.blps.delegate;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.identity.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import lab.blps.security.bd.entities.user.RoleEnum;
import lab.blps.security.services.UserService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RequestFeeDelegate implements JavaDelegate {
    @Value("${request-fee}")
    private Integer requestFee;

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
        if (!userService.isAmountRequestEnough(userId)) {
            throw new BpmnError("notEnoughAmountRequest");
        }
        try {
            userService.subAmountRequest(userId, requestFee);
        } catch (DataIntegrityViolationException e) {
            throw new BpmnError("notEnoughAmountRequest");
        }
    }
}
