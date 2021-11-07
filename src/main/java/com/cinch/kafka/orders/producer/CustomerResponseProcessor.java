package com.cinch.kafka.orders.producer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CustomerResponseProcessor {

    @KafkaListener(topics = "response-topic", groupId = "group1")
    public void listen(String message) {
        System.out.println("Reading response from response-topic: " + message);
    }
}
