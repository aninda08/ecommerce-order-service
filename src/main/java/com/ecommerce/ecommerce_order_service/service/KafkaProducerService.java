package com.ecommerce.ecommerce_order_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce_order_service.model.UserMessage;

@Service
public class KafkaProducerService {

    @Autowired
    KafkaTemplate<String, UserMessage> kafkaTemplate;

    public void sendMessage(UserMessage message) {
        kafkaTemplate.send("ecommerce-order-topic", message);
    }
}
