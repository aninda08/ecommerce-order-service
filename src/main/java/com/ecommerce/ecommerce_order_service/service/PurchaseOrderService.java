package com.ecommerce.ecommerce_order_service.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.ecommerce_order_service.exception.CustomErrorCode;
import com.ecommerce.ecommerce_order_service.exception.OrderServiceException;
import com.ecommerce.ecommerce_order_service.request.OrderRequest;
import com.ecommerce.ecommerce_order_service.response.SuccessResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

@Service
public class PurchaseOrderService {

    @Autowired
    private S3Service s3Service;

    public ResponseEntity<SuccessResponse> createPurchaseOrder(OrderRequest order) {
        try {
            String key = order.getOrderNumber() + ".json";
            ObjectMapper mapper = new ObjectMapper();
            String content = mapper.writeValueAsString(order);

            s3Service.uploadFile(key, content);
            return new ResponseEntity<>(new SuccessResponse("Purchase Order created successfully", "success"), HttpStatus.OK);
        }
        catch (Exception e) {
            throw new RuntimeException("Purchase Order creation failed", e);
        }
    }

    public ResponseEntity<SuccessResponse> uploadPurchaseOrder(MultipartFile file) {
        try {
            String key = file.getOriginalFilename();
            s3Service.upload(key, file.getInputStream(), file.getSize(), file.getContentType());
            return new ResponseEntity<>(new SuccessResponse("File uploaded successfully", "success"), HttpStatus.OK);
        } catch (IOException e) {
            throw new OrderServiceException("File upload failed", CustomErrorCode.GENERIC_FILE_UPLOAD_ERROR_CODE);
        }
    }

    public ResponseEntity<byte[]> downloadPurchaseOrder(String key) {
        try {
            byte[] data = s3Service.download(key);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + key + "\"")
                    .body(data);
        } catch (NoSuchKeyException e) {
            throw new OrderServiceException("File download failed", CustomErrorCode.GENERIC_FILE_DOWNLOAD_ERROR_CODE);
        }
    }

    public ResponseEntity<SuccessResponse> deletePurchaseOrder(String key) {
        try {
            s3Service.deleteFile(key);
            return new ResponseEntity<>(new SuccessResponse("File deleted successfully", "success"), HttpStatus.OK);
        } catch (NoSuchKeyException e) {
            throw new OrderServiceException("File Not Found", CustomErrorCode.GENERIC_FILE_DELETE_ERROR_CODE);
        }
        catch (Exception e) {
            throw new OrderServiceException("File deletion failed", CustomErrorCode.GENERIC_FILE_DELETE_ERROR_CODE);
        }
    }

    public ResponseEntity<List<String>> listPurchaseOrder() {
        try {
            List<String> files = s3Service.listFiles();
            return new ResponseEntity<>(files, HttpStatus.OK);
        } catch (Exception e) {
            throw new OrderServiceException("File listing failed", CustomErrorCode.GENERIC_FILE_LISTING_ERROR_CODE);
        }
    }
    
}
