package com.ecommerce.ecommerce_order_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce_order_service.exception.CustomErrorCode;
import com.ecommerce.ecommerce_order_service.exception.OrderServiceException;
import com.ecommerce.ecommerce_order_service.request.OrderRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.CreateTopicRequest;
import software.amazon.awssdk.services.sns.model.PublishRequest;

@Service
public class SnsService {
    private final SnsClient snsClient;
    private String topicArn;

    @Value("${aws.sns.purchase-order-topic-name}")
    private String topicName;

    @Value("${aws.sns.purchase-order-topic-group}")
    private String topicGroup;

    public SnsService(SnsClient snsClient) {
        this.snsClient = snsClient;
    }

    @PostConstruct
    public void createTopic() {
        CreateTopicRequest request = CreateTopicRequest.builder()
                .name(topicName)
                .attributes(Map.of(
                    "FifoTopic", "true",
                    "ContentBasedDeduplication", "true"
                ))
                .build();
        topicArn = snsClient.createTopic(request).topicArn();
    }

    public void publish(String message) {
        PublishRequest request = PublishRequest.builder()
                .topicArn(topicArn)
                .message(message)
                .build();
        snsClient.publish(request);
    }

    public void publish(OrderRequest message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String messageBody = mapper.writeValueAsString(message);
            // Generate a unique deduplication ID based on the order ID
            String messageDeduplicationId = messageBody.hashCode() + "";
            
            PublishRequest request = PublishRequest.builder()
                    .topicArn(topicArn)
                    .message(messageBody)
                    .messageGroupId(topicGroup)
                    .messageDeduplicationId(messageDeduplicationId)
                    .build();
            snsClient.publish(request);
        }
        catch(Exception e) {
            e.printStackTrace();
            throw new OrderServiceException("Failed to publish purchase order details to SNS", CustomErrorCode.GENERIC_SQS_SEND_ERROR_CODE);
        }
        
    }

    public String getTopicArn() {
        return topicArn;
    }

    public SnsClient getSnsClient() {
        return snsClient;
    }

}
