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
@Table(name = "ORDERS")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID",nullable = false)
    private int id;

    @Column(name="ORDER_NUMBER")
    private String orderNumber;

    @Column(name="CUSTOMER_ID")
    private int customerId;

    @Column(name="TOTAL_AMOUNT")
    private int totalAmount;

    @Column(name="STATUS")
    private String status;

    @Column(name="PAYMENT_STATUS")
    private String paymentStatus;

    @Column(name="PAYMENT_MODE")
    private String paymentMode;

    @Column(name="SHIPPING_ADDRESS")
    private String shippingAddress;

    @Column(name = "ORDER_DATE", columnDefinition = "TIMESTAMP")
    @JsonFormat(shape = Shape.STRING, pattern = "MM-dd-yyyy HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

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
