package com.ecommerce.ecommerce_order_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductItem {
    private int productId;
    private int quantity;
}
