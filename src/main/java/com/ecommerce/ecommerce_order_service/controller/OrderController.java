package com.ecommerce.ecommerce_order_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce_order_service.request.OrderRequest;
import com.ecommerce.ecommerce_order_service.response.OrderResponse;
import com.ecommerce.ecommerce_order_service.response.SuccessResponse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private com.ecommerce.ecommerce_order_service.service.OrderService OrderService;
    @GetMapping("/v1/all")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return OrderService.getAllOrders();
    }

    @GetMapping("/v1/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable int id) {
        return OrderService.getOrderById(id);
    }

    @PostMapping("/v1/add")
    public ResponseEntity<SuccessResponse> addOrder(@RequestBody OrderRequest order) {
        return OrderService.addOrder(order);
    }

    @PutMapping("/v1/{id}")
    public ResponseEntity<SuccessResponse> updateProduct(@RequestBody OrderRequest order,@PathVariable int id) {
        order.setId(id);
        return OrderService.updateOrder(order);
    }


    @DeleteMapping("/v1/{id}")
    public ResponseEntity<SuccessResponse> deleteOrder(@PathVariable int id) {
        return OrderService.deleteOrder(id);
    }
}
 