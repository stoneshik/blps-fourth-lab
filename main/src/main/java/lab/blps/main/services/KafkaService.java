package lab.blps.main.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lab.blps.security.dto.request.AddAmountRequestDto;

@Service
public class KafkaService {
	@Autowired
	private KafkaTemplate<Long, AddAmountRequestDto> kafkaTemplate;

	@Value("${spring.kafka.producer.topic}")
	private String topicName;

	public void sendMessage(AddAmountRequestDto addAmountRequestDto) {
		kafkaTemplate.send(topicName, addAmountRequestDto);
	}
}
