package com.ecommerce.ecommerce_order_service.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private String description;
    private String message;
    private String code;

    private ErrorResponse() {
    }
    
    public static ErrorResponse generatErrorResponse(String code, String message) {
        ErrorResponse error = new ErrorResponse();
        error.setCode(code);
        error.setMessage(message);
        return error;
    }
}
