package lab.blps.delegate;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.identity.Authentication;
import org.springframework.stereotype.Component;

import lab.blps.main.services.KafkaService;
import lab.blps.security.bd.entities.user.RoleEnum;
import lab.blps.security.dto.request.AddAmountRequestDto;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AddAmountRequestDelegate implements JavaDelegate {
    private final KafkaService kafkaService;
    private final IdentityService identityService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Authentication authentication = identityService.getCurrentAuthentication();
        if (!authentication.getGroupIds().contains(RoleEnum.ADMIN.name())) {
            throw new BpmnError("unauthorized");
        }
        AddAmountRequestDto addAmountRequestDto = createAddAmountRequestDto(delegateExecution);
        kafkaService.sendMessage(addAmountRequestDto);
        delegateExecution.setVariable("response", "Пользователю успешно добавлено количество запросов!");
    }

    private AddAmountRequestDto createAddAmountRequestDto(DelegateExecution delegateExecution) {
        String userId = (String) delegateExecution.getVariable("userId");
        Integer amountRequest = (Integer) delegateExecution.getVariable("amountRequest");
        return new AddAmountRequestDto(
            userId,
            amountRequest
        );
    }
}
