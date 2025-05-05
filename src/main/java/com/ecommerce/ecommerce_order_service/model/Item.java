package com.ecommerce.ecommerce_order_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Item {
    private int productId;
    private String productName;
    private int quantity;
    private double unitPrice;
    private double totalPrice;

}
