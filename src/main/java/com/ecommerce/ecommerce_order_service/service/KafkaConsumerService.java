package com.ecommerce.ecommerce_order_service.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.ecommerce.ecommerce_order_service.model.UserMessage;

@Component
public class KafkaConsumerService {

    @KafkaListener(topics = "ecommerce-order-topic", groupId = "json-group")
    public UserMessage listen(UserMessage message) {
        System.out.println("Received message: " + message);
        return message;
    }
}
