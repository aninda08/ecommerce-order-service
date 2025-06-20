package com.ecommerce.ecommerce_order_service.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce_order_service.exception.CustomErrorCode;
import com.ecommerce.ecommerce_order_service.exception.OrderServiceException;
import com.ecommerce.ecommerce_order_service.request.OrderRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlResponse;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.QueueAttributeName;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Service
public class SqsService {
    private final SqsClient sqsClient;

    @Value("${aws.sqs.purchase-order-queue-name}")
    private String purchaseOrderQueueName;

    @Value("${aws.sqs.purchase-order-queue-group}")
    private String purchaseOrderQueueGroup;

    private String purchaseOrderQueueUrl;

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    public SqsService(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    @PostConstruct
    public void init() {
        Map<QueueAttributeName, String> attributes = new HashMap<>();
        attributes.put(QueueAttributeName.FIFO_QUEUE, "true");
        attributes.put(QueueAttributeName.CONTENT_BASED_DEDUPLICATION, "true");
        
        CreateQueueRequest createQueueRequest = CreateQueueRequest.builder()
                .queueName(purchaseOrderQueueName)
                .attributes(attributes)
                .build();
        sqsClient.createQueue(createQueueRequest);

        GetQueueUrlResponse queueUrlResponse = sqsClient.getQueueUrl(GetQueueUrlRequest.builder()
                .queueName(purchaseOrderQueueName).build());
        purchaseOrderQueueUrl = queueUrlResponse.queueUrl();
    }

    public void sendPurchaseOrderDetails(OrderRequest purchaseOrderDetails) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String messageBody = mapper.writeValueAsString(purchaseOrderDetails);
            SendMessageRequest request = SendMessageRequest.builder()
                    .queueUrl(purchaseOrderQueueUrl)
                    .messageBody(messageBody)
                    .messageGroupId(purchaseOrderQueueGroup)
                    .build();
            sqsClient.sendMessage(request);
        }
        catch(Exception e) {
            throw new OrderServiceException("Failed to send purchase order details to SQS", CustomErrorCode.GENERIC_SQS_SEND_ERROR_CODE);
        }
    }

    public List<Message> receivePurchaseOrderDetails() {
        ReceiveMessageRequest request = ReceiveMessageRequest.builder()
                .queueUrl(purchaseOrderQueueUrl)
                .maxNumberOfMessages(5)
                .waitTimeSeconds(1)
                .build();
        return sqsClient.receiveMessage(request).messages();
    }

    public void deletePurchaseOrderDetails(String receiptHandle) {
        DeleteMessageRequest request = DeleteMessageRequest.builder()
                .queueUrl(purchaseOrderQueueUrl)
                .receiptHandle(receiptHandle)
                .build();
        sqsClient.deleteMessage(request);
    }

    @Scheduled(fixedRate = 5000) // every 5 seconds
    public void pollQueue() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Message> messages = receivePurchaseOrderDetails();
            for (Message message : messages) {
                System.out.println("📥 Received: " + message.body());
                OrderRequest purchaseOrderDetails = mapper.readValue(message.body(), OrderRequest.class);
                // Process the message
                processPurchaseOrderDetails(purchaseOrderDetails);

                // Delete after processing
                deletePurchaseOrderDetails(message.receiptHandle());
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            throw new OrderServiceException("Failed to receive purchase order details from SQS", CustomErrorCode.GENERIC_SQS_RECEIVE_ERROR_CODE);
        }
        
    }

    private void processPurchaseOrderDetails(OrderRequest purchaseOrderDetails) {
        purchaseOrderService.createPurchaseOrder(purchaseOrderDetails);
    }
}
