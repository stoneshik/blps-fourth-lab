package lab.blps.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import lab.blps.main.services.KafkaService;
import lab.blps.security.dto.request.AddAmountRequestDto;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AddAmountRequestDelegate implements JavaDelegate {
    private final KafkaService kafkaService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        AddAmountRequestDto addAmountRequestDto = createAddAmountRequestDto(delegateExecution);
        kafkaService.sendMessage(addAmountRequestDto);
        delegateExecution.setVariable("response", "Пользователю успешно добавлено количество запросов!");
    }

    private AddAmountRequestDto createAddAmountRequestDto(DelegateExecution delegateExecution) {
        Long userId = ((Number) delegateExecution.getVariable("userId")).longValue();
        Integer amountRequest = (Integer) delegateExecution.getVariable("amountRequest");
        return new AddAmountRequestDto(
            userId,
            amountRequest
        );
    }
}
