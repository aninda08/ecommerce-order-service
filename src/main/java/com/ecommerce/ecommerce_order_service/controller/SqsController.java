package com.ecommerce.ecommerce_order_service.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce_order_service.request.OrderRequest;
import com.ecommerce.ecommerce_order_service.service.SqsService;

import software.amazon.awssdk.services.sqs.model.Message;

@RestController
@RequestMapping("/api/sqs")
public class SqsController {
    private final SqsService sqsService;

    public SqsController(SqsService sqsService) {
        this.sqsService = sqsService;
    }

    @PostMapping("/v1/send")
    public ResponseEntity<String> sendPurchaseOrderDetails(@RequestBody OrderRequest purchaseOrderDetails) {
        sqsService.sendPurchaseOrderDetails(purchaseOrderDetails);
        return ResponseEntity.ok("Message sent!");
    }

    @GetMapping("/v1/receive")
    public ResponseEntity<List<Map<String, String>>> receivePurchaseOrderDetails() {
        List<Message> messages = sqsService.receivePurchaseOrderDetails();

        List<Map<String, String>> response = messages.stream()
                .map(msg -> Map.of(
                        "messageId", msg.messageId(),
                        "body", msg.body(),
                        "receiptHandle", msg.receiptHandle()
                )).toList();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/v1/delete/{receiptHandle}")
    public ResponseEntity<String> deletePurchaseOrderDetails(@PathVariable String receiptHandle) {
        sqsService.deletePurchaseOrderDetails(receiptHandle);
        return ResponseEntity.ok("Message deleted");
    }
}
