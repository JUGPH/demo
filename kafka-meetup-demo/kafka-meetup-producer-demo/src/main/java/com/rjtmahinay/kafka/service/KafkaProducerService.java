package com.rjtmahinay.kafka.service;

import com.rjtmahinay.kafka.avro.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, Message> kafkaTemplate;

    @Value("${jugph.topic.name}")
    private String topicName;

    public KafkaProducerService(KafkaTemplate<String, Message> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String content) {
        Message message = Message.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setContent(content)
                .setTimestamp(System.currentTimeMillis())
                .build();

        kafkaTemplate.send(topicName, message.getId(), message);
    }
}
