package com.ecommerce.ecommerce_order_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce_order_service.service.AWSParameterStoreService;
import com.ecommerce.ecommerce_order_service.service.AWSSecretsService;

@RestController
@RequestMapping("/api/order")
public class AWSConfigController {
    @Autowired
    private AWSParameterStoreService awsParameterStoreService;

    @Autowired
    private AWSSecretsService awsSecretsService;

    @Value("${aws.ssm.eureka-url}")
    private String eurekaUrl;

    @Value("${aws.secretsmanager.db-credentials}")
    private String dbCredentials;

    @GetMapping("/v1/secret")
    public ResponseEntity<String> getSecret() {
        return ResponseEntity.ok(awsSecretsService.getSecret(dbCredentials));
    }

    @GetMapping("/v1/parameter")
    public ResponseEntity<String> getParameter() {
        return ResponseEntity.ok(awsParameterStoreService.getParameter(eurekaUrl));
    }
}
