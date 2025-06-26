package com.ecommerce.ecommerce_order_service.service;

import org.springframework.stereotype.Service;

import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;

@Service
public class AWSSecretsService {

    private final SecretsManagerClient secretsManagerClient;

    public AWSSecretsService(SecretsManagerClient secretsManagerClient) {
        this.secretsManagerClient = secretsManagerClient;
    }

    public String getSecret(String secretName) {
        GetSecretValueRequest request = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();
        return secretsManagerClient.getSecretValue(request).secretString();
    }

}
