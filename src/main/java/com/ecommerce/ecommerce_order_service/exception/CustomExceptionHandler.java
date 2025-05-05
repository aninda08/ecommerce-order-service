package com.ecommerce.ecommerce_order_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;

import com.ecommerce.ecommerce_order_service.response.ErrorResponse;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericExceptions(Exception ex) {
        ErrorResponse errorResponse = ErrorResponse
                .generatErrorResponse(CustomErrorCode.GENERIC_INTERNAL_SERVER_ERROR_CODE, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<Object> handleValidationExceptions(WebExchangeBindException ex) {
        ErrorResponse errorResponse = ErrorResponse
                .generatErrorResponse(CustomErrorCode.GENERIC_REQUEST_VALIDATION_ERROR_CODE, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServerWebInputException.class)
    public ResponseEntity<Object> handleValidationExceptions(ServerWebInputException ex) {
        ErrorResponse errorResponse = ErrorResponse
                .generatErrorResponse(CustomErrorCode.GENERIC_REQUEST_VALIDATION_ERROR_CODE, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    

    @ExceptionHandler(DetailsNotFoundException.class)
    public ResponseEntity<Object> handleDetailsNotFoundExceptions(DetailsNotFoundException ex) {
        ErrorResponse errorResponse = ErrorResponse
                .generatErrorResponse(CustomErrorCode.GENERIC_REQUEST_VALIDATION_ERROR_CODE, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderServiceException.class)
    public ResponseEntity<Object> handleOrderServiceExceptions(OrderServiceException ex) {
        ErrorResponse errorResponse = ErrorResponse.generatErrorResponse(ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
