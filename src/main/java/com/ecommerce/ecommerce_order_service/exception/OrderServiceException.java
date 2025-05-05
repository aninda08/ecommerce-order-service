package com.ecommerce.ecommerce_order_service.exception;

import lombok.Getter;

public class OrderServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    @Getter
    protected final String code;

    public OrderServiceException(String message, String code) {
        super(message);
        this.code = code;
    }
}
