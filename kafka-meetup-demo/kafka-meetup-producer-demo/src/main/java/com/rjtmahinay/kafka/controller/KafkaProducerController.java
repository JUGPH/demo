package com.rjtmahinay.kafka.controller;

import com.rjtmahinay.kafka.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KafkaProducerController {

    private final KafkaProducerService kafkaProducerService;

    @PostMapping("/send")
    public void sendMessage(@RequestBody String message) {
        kafkaProducerService.sendMessage(message);
    }
}
