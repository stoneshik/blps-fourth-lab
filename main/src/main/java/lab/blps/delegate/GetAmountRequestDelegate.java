package lab.blps.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import lab.blps.security.services.UserService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GetAmountRequestDelegate implements JavaDelegate {
    private final UserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Long userId = ((Number) delegateExecution.getVariable("userId")).longValue();
        Integer amountRequest = userService.loadAmountRequest(userId);
        delegateExecution.setVariable("response", amountRequest);
    }
}
