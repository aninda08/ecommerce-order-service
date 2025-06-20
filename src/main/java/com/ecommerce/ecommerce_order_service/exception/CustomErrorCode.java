package com.ecommerce.ecommerce_order_service.exception;

public class CustomErrorCode {
    private CustomErrorCode() {
        throw new IllegalStateException("CustomCode class");
    }

    //400 series
    public static final String GENERIC_REQUEST_VALIDATION_ERROR_CODE = "4001";
    public static final String GENERIC_FILE_UPLOAD_ERROR_CODE = "4002";
    public static final String GENERIC_FILE_DOWNLOAD_ERROR_CODE = "4003";
    public static final String GENERIC_FILE_DELETE_ERROR_CODE = "4004";
    public static final String GENERIC_FILE_LISTING_ERROR_CODE = "4005";
    public static final String GENERIC_SQS_SEND_ERROR_CODE = "4006";
    public static final String GENERIC_SQS_RECEIVE_ERROR_CODE = "4007";
    public static final String GENERIC_SQS_DELETE_ERROR_CODE = "4008";

    //500 series
    public static final String GENERIC_INTERNAL_SERVER_ERROR_CODE = "5001";
    public static final String GENERIC_ORDER_REPOSITORY_EXCEPTION_CODE = "5002";
}
