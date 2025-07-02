package com.ecommerce.ecommerce_order_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce_order_service.constants.Constants;
import com.ecommerce.ecommerce_order_service.model.User;

@Service
public class KafkaProducerService {

    @Autowired
    KafkaTemplate<String, User> kafkaTemplate;

    public void sendMessage(User message) {
        kafkaTemplate.send(Constants.TOPIC_NAME, message);
    }
}
