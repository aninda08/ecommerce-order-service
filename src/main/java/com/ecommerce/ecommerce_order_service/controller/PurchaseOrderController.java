package com.ecommerce.ecommerce_order_service.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.ecommerce_order_service.request.OrderRequest;
import com.ecommerce.ecommerce_order_service.response.SuccessResponse;
import com.ecommerce.ecommerce_order_service.service.PurchaseOrderService;

@RestController
@RequestMapping("/api/purchaseorder")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;
    
    @PostMapping("/v1/create")
    public ResponseEntity<SuccessResponse> createPurchaseOrder(@RequestBody OrderRequest order) {
        return purchaseOrderService.createPurchaseOrder(order);
    }

    @PostMapping("/v1/upload")
    public ResponseEntity<SuccessResponse> uploadPurchaseOrder(@RequestParam("file") MultipartFile file) {
        return purchaseOrderService.uploadPurchaseOrder(file);
    }

    @GetMapping("/v1/download/{key}")
    public ResponseEntity<byte[]> downloadPurchaseOrder(@PathVariable String key) {
        return purchaseOrderService.downloadPurchaseOrder(key);
    }

    @GetMapping("/v1/list")
    public ResponseEntity<List<String>> listPurchaseOrder() {
        return purchaseOrderService.listPurchaseOrder();
    }

    @DeleteMapping("/v1/delete/{key}")
    public ResponseEntity<SuccessResponse> deletePurchaseOrder(@PathVariable String key) {
        return purchaseOrderService.deletePurchaseOrder(key);
    }
}
