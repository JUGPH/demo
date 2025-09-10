package com.rjtmahinay.kafka.service;

import com.rjtmahinay.kafka.avro.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerService {

    @KafkaListener(
            topics = "${jugph.topic.name}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(@Payload Message message) {
        log.info("Received message: {}", message);
    }

}
