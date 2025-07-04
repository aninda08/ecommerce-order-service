package com.ecommerce.ecommerce_order_service.service;

import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce_order_service.constants.Constants;
import com.ecommerce.ecommerce_order_service.model.User;

@Service
public class KafkaConsumerService {
    private int failureCount = 0;
    private int maxFailureCount = 5;

    // @Header(KafkaHeaders.TOPIC) String topic, @Header(KafkaHeaders.OFFSET) long offset
    @RetryableTopic(attempts = "4", backoff = @Backoff(delay = 1000))
    @KafkaListener(topics = Constants.TOPIC_NAME, groupId = Constants.GROUP_ID)
    public void listen(User message) {
        try {
            System.out.println("Received message: " + message);
            if(failureCount < maxFailureCount) {
                failureCount++;
                System.out.println("Failure message: " + message + ", failure count: " + failureCount);
                throw new RuntimeException("Test failure: " + failureCount);
            }
            System.out.println("Success message: " + message);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @DltHandler
    public void listenDeadLetter(User message, @Header(KafkaHeaders.TOPIC) String topic, @Header(KafkaHeaders.OFFSET) long offset) {
        System.out.println("DLT Received message: " + message + " from topic: " + topic + " offset: " + offset);
        throw new RuntimeException("Message failed after retries and moved to DLT");
    }
}
