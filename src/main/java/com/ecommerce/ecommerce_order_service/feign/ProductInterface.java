package com.ecommerce.ecommerce_order_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ecommerce.ecommerce_order_service.request.ProductRequest;

// @FeignClient("ECOMMERCE-PRODUCT-SERVICE")
@FeignClient(name = "ECOMMERCE-PRODUCT-SERVICE", url = "http://localhost:8082")
public interface ProductInterface {
    @GetMapping("api/product/v1/{id}")
    public ResponseEntity<ProductRequest> getProductById(@PathVariable int id);
}
