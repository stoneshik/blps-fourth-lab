package lab.blps.components;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lab.blps.dto.AddAmountRequestDto;
import lab.blps.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumerListener {
	private final UserService userService;
	private final ObjectMapper objectMapper;

	@KafkaListener(
		topics = "${spring.kafka.topic.name}",
		groupId = "${spring.kafka.group-id}",
		containerFactory = "singleFactory"
	)
	public void listen(AddAmountRequestDto addAmountRequestDto) {
		log.info("=> consumed {}", writeValueAsString(addAmountRequestDto));
		userService.addAmountRequest(
            addAmountRequestDto.getUserId(),
            addAmountRequestDto.getAmountRequest()
        );
	}

	@Scheduled(cron = "0 0 0 * * *")
	public void scheduleDailyAddingAmount() {
		final Integer AMOUNT_ADDING_REQUESTS = 10;
		userService.addAllUserAmountRequest(AMOUNT_ADDING_REQUESTS);
	}

	private String writeValueAsString(AddAmountRequestDto addAmountRequestDto) {
        try {
            return objectMapper.writeValueAsString(addAmountRequestDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Writing value to JSON failed: " + addAmountRequestDto.toString());
        }
    }
}
