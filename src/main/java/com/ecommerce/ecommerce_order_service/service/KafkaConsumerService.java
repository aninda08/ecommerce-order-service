package com.ecommerce.ecommerce_order_service.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.ecommerce.ecommerce_order_service.constants.Constants;
import com.ecommerce.ecommerce_order_service.model.User;

@Component
public class KafkaConsumerService {

    @KafkaListener(topics = Constants.TOPIC_NAME, groupId = Constants.GROUP_ID)
    public void listen(User message) {
        System.out.println("Received message: " + message);
    }
}
