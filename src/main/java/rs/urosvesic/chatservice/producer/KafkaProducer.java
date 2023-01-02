package rs.urosvesic.chatservice.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import rs.urosvesic.chatservice.dto.MessageResponse;

@RequiredArgsConstructor
//@Component
public class KafkaProducer {

    private final KafkaTemplate<String, MessageResponse> kafkaTemplate;

    @Value("${producer.topic.name}")
    private String topicName;

    @Async
    public void notifyUserAboutNewMessage(MessageResponse messageResponse){
        kafkaTemplate.send(topicName, messageResponse);
    }
}
