package com.ecommerce.ecommerce_order_service.service;

import org.springframework.stereotype.Service;

import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;

@Service
public class AWSParameterStoreService {
    private final SsmClient ssmClient;

    public AWSParameterStoreService(SsmClient ssmClient) {
        this.ssmClient = ssmClient;
    }

    public String getParameter(String parameterName) {
        GetParameterRequest request = GetParameterRequest.builder()
                .name(parameterName)
                .withDecryption(true)
                .build();
        return ssmClient.getParameter(request).parameter().value();
    }
}
