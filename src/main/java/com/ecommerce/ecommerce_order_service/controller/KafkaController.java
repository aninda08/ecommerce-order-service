package com.ecommerce.ecommerce_order_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce_order_service.model.User;
import com.ecommerce.ecommerce_order_service.service.KafkaProducerService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody User message) {
        kafkaProducerService.sendMessage(message);
        return ResponseEntity.ok("Message sent successfully");
    }
}
