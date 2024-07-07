package lab.blps.main.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {
	private KafkaTemplate<String, String> kafkaTemplate;

	@Value("${spring.kafka.producer.topic}")
	private String topicName;

	public KafkaService(@Autowired KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void sendMessage(String msg) {
		kafkaTemplate.send(topicName, msg);
	}
}
