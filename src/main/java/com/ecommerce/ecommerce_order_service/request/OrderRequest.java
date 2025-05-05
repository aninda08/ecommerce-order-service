package com.ecommerce.ecommerce_order_service.request;

import java.util.List;

import com.ecommerce.ecommerce_order_service.model.ProductItem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderRequest {

    private int id;
    private String orderNumber;
    private int customerId;
    private String paymentMode;
    private String paymentStatus;
    private String shippingAddress;
    private List<ProductItem> products;

}
