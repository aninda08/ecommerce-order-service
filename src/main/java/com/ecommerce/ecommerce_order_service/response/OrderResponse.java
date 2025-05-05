package com.ecommerce.ecommerce_order_service.response;

import java.util.Date;
import java.util.List;

import com.ecommerce.ecommerce_order_service.model.Item;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse {
    private int id;
    private String orderNumber;
    private int customerId;
    private double totalAmount;
    private String status;
    private String paymentStatus;
    private String paymentMode;
    private String shippingAddress;
    private Date orderDate;

    private List<Item> items;
}
