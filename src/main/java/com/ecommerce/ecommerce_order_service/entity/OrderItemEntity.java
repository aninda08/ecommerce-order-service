package com.ecommerce.ecommerce_order_service.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ORDER_ITEMS")
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID",nullable = false)
    private int id;

    @Column(name="ORDER_ID")
    private int orderId;

    @Column(name="PRODUCT_ID")
    private int productId;

    @Column(name="PRODUCT_NAME")
    private String productName;

    @Column(name="QUANTITY")
    private int quantity;

    @Column(name="UNIT_PRICE")
    private double unitPrice;

    @Column(name="TOTAL_PRICE")
    private double totalPrice;

    @Column(name="UPDATED_BY")
    private String updatedBy;

    @Column(name = "CREATED_TIME", columnDefinition = "TIMESTAMP", insertable = false, updatable = false)
    @JsonFormat(shape = Shape.STRING, pattern = "MM-dd-yyyy HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @Column(name = "UPDATED_TIME", columnDefinition = "TIMESTAMP", insertable = true, updatable = true)
    @JsonFormat(shape = Shape.STRING, pattern = "MM-dd-yyyy HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTime;

    @Column(name = "DELETED_TIME", columnDefinition = "TIMESTAMP")
    @JsonFormat(shape = Shape.STRING, pattern = "MM-dd-yyyy HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedTime;
}
