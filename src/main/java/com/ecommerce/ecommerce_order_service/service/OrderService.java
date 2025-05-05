package com.ecommerce.ecommerce_order_service.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce_order_service.entity.OrderEntity;
import com.ecommerce.ecommerce_order_service.entity.OrderItemEntity;
import com.ecommerce.ecommerce_order_service.exception.DetailsNotFoundException;
import com.ecommerce.ecommerce_order_service.feign.OrderInterface;
import com.ecommerce.ecommerce_order_service.model.Item;
import com.ecommerce.ecommerce_order_service.model.PaymentStatus;
import com.ecommerce.ecommerce_order_service.model.ProductItem;
import com.ecommerce.ecommerce_order_service.repository.OrderItemRepository;
import com.ecommerce.ecommerce_order_service.repository.OrderRepository;
import com.ecommerce.ecommerce_order_service.request.OrderRequest;
import com.ecommerce.ecommerce_order_service.request.ProductRequest;
import com.ecommerce.ecommerce_order_service.response.OrderResponse;
import com.ecommerce.ecommerce_order_service.response.SuccessResponse;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderInterface orderInterface;

    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<OrderEntity> orders = orderRepository.findAllOrder();

        List<OrderResponse> orderResponses = new ArrayList<>();
        for (OrderEntity orderEntity : orders) {
            
            List<OrderItemEntity> orderItemEntities = orderItemRepository.getByOrderId(orderEntity.getId());
            
            List<Item> items = orderItemEntities.stream().map(orderItem -> new Item(orderItem.getProductId(),orderItem.getProductName(),orderItem.getQuantity(),orderItem.getUnitPrice(),orderItem.getTotalPrice()))
                    .collect(Collectors.toList());

            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setId(orderEntity.getId());
            orderResponse.setOrderNumber(orderEntity.getOrderNumber());
            orderResponse.setCustomerId(orderEntity.getCustomerId());
            orderResponse.setTotalAmount(orderEntity.getTotalAmount());
            orderResponse.setStatus(orderEntity.getStatus());
            orderResponse.setPaymentStatus(orderEntity.getPaymentStatus());
            orderResponse.setPaymentMode(orderEntity.getPaymentMode());
            orderResponse.setShippingAddress(orderEntity.getShippingAddress());
            orderResponse.setOrderDate(orderEntity.getOrderDate());
            orderResponse.setItems(items);
            orderResponses.add(orderResponse);
        }

        return new ResponseEntity<>(orderResponses, HttpStatus.OK);
    } 

    public ResponseEntity<SuccessResponse> addOrder(OrderRequest orderRequest) {
        if (orderRequest == null
                || orderRequest.getOrderNumber().isEmpty()
                || orderRequest.getPaymentStatus().equalsIgnoreCase(PaymentStatus.UNPAID.toString())
                || orderRequest.getShippingAddress().isEmpty()
                || (orderRequest.getProducts().size() == 0)) {
            throw new IllegalArgumentException("Invalid order request");
        }

        int totalAmount = 0;

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderNumber(orderRequest.getOrderNumber());
        orderEntity.setCustomerId(orderRequest.getCustomerId());
        orderEntity.setPaymentMode(orderRequest.getPaymentMode());
        orderEntity.setPaymentStatus(orderRequest.getPaymentStatus());
        orderEntity.setShippingAddress(orderRequest.getShippingAddress());
        OrderEntity savedOrder = orderRepository.save(orderEntity);

        for (ProductItem product : orderRequest.getProducts()) {
            ProductRequest productEntity = orderInterface.getProductById(product.getProductId()).getBody();
            if (productEntity == null) {
                continue;
            }
            double totalPrice = 0;
            totalPrice = productEntity.getPrice() * product.getQuantity();

            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setOrderId(savedOrder.getId());
            orderItem.setProductId(product.getProductId());
            orderItem.setQuantity(product.getQuantity());
            orderItem.setUpdatedBy("Admin");
            orderItem.setTotalPrice(totalPrice);
            orderItem.setUnitPrice(productEntity.getPrice());

            orderItemRepository.save(orderItem);

            totalAmount += totalPrice;
        }

        savedOrder.setTotalAmount(totalAmount);
        orderRepository.save(savedOrder);

        return new ResponseEntity<>(new SuccessResponse("Order created successfully", "success"), HttpStatus.OK);
    }

    public ResponseEntity<OrderResponse> getOrderById(int id) {
        Optional<OrderEntity> orderOptional = orderRepository.findById(id);

        if (!orderOptional.isPresent()) {
            throw new DetailsNotFoundException("Order not found for order id: " + id);
        }

        OrderEntity orderEntity = orderOptional.get();
        List<OrderItemEntity> orderItemEntities = orderItemRepository.getByOrderId(orderEntity.getId());

        List<Item> items = orderItemEntities.stream()
                .map(orderItem -> new Item(orderItem.getProductId(), "", orderItem.getQuantity(),
                        orderItem.getUnitPrice(), orderItem.getTotalPrice()))
                .collect(Collectors.toList());

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(orderEntity.getId());
        orderResponse.setOrderNumber(orderEntity.getOrderNumber());
        orderResponse.setCustomerId(orderEntity.getCustomerId());
        orderResponse.setTotalAmount(orderEntity.getId());
        orderResponse.setStatus(orderEntity.getStatus());
        orderResponse.setPaymentStatus(orderEntity.getPaymentStatus());
        orderResponse.setPaymentMode(orderEntity.getPaymentMode());
        orderResponse.setShippingAddress(orderEntity.getShippingAddress());
        orderResponse.setOrderDate(orderEntity.getOrderDate());
        orderResponse.setItems(items);

        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }
    
    public ResponseEntity<SuccessResponse> updateOrder(OrderRequest order) {
        Optional<OrderEntity> orderOptional = orderRepository.findById(order.getId());

        if (!orderOptional.isPresent()) {
            throw new DetailsNotFoundException("Order not found for order id: " + order.getId());
        }

        int totalAmount = 0;
        OrderEntity orderEntity = orderOptional.get();
        
        orderEntity.setPaymentMode(order.getPaymentMode());
        orderEntity.setPaymentStatus(order.getPaymentStatus());
        orderEntity.setShippingAddress(order.getShippingAddress());
        orderEntity.setUpdatedBy("Admin");
        orderEntity.setUpdatedTime(new Date());
        orderRepository.save(orderEntity);

        for (ProductItem product : order.getProducts()) {
            List<OrderItemEntity> orderItem = orderItemRepository.findByOrderIdAndProductId(orderEntity.getId(),
                    product.getProductId());
            
            if (orderItem.size() > 0) {
                orderItem.get(0).setQuantity(product.getQuantity());
                orderItem.get(0).setTotalPrice(product.getQuantity() * orderItem.get(0).getUnitPrice());
                orderItemRepository.save(orderItem.get(0));

                totalAmount += (product.getQuantity() * orderItem.get(0).getUnitPrice());
            } else {

                ProductRequest productEntity = orderInterface.getProductById(product.getProductId()).getBody();
                if (productEntity == null) {
                    continue;
                }
                double totalPrice = 0;
                totalPrice = productEntity.getPrice() * product.getQuantity();

                OrderItemEntity newOrder = new OrderItemEntity();
                newOrder.setOrderId(orderEntity.getId());
                newOrder.setProductId(product.getProductId());
                newOrder.setQuantity(product.getQuantity());
                newOrder.setUpdatedBy("Admin");
                newOrder.setUnitPrice(productEntity.getPrice());
                newOrder.setTotalPrice(totalPrice);
                
                orderItemRepository.save(newOrder);

                totalAmount += totalPrice;
            }
        }
        
        orderEntity.setTotalAmount(totalAmount);
        orderRepository.save(orderEntity);

        return new ResponseEntity<>(new SuccessResponse("Order updated successfully", "success"), HttpStatus.OK);
    }
    
    public ResponseEntity<SuccessResponse> deleteOrder(int id) {
        Optional<OrderEntity> order = orderRepository.findById(id);
        if (order.isPresent()) {
            order.get().setDeletedTime(new Date());
            orderRepository.save(order.get());

            List<OrderItemEntity> orderItemEntities = orderItemRepository.getByOrderId(order.get().getId());
            
            for(OrderItemEntity orderItemEntity: orderItemEntities) {
                orderItemEntity.setDeletedTime(new Date());
                orderItemRepository.save(orderItemEntity);
            }

            return new ResponseEntity<>(new SuccessResponse("Order deleted successfully", "success"),
                    HttpStatus.OK);
        }
        throw new DetailsNotFoundException("Order not found");
    } 
}
