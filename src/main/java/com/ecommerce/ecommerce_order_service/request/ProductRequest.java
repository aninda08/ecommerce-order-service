package com.ecommerce.ecommerce_order_service.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor 
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductRequest {
    private int id;
    private String name;
    private String description;
    private int price;
    private int mrp;
    private int stockQuantity;
    private String category;
    private String size;
    private String color;
    private String imageUrl;
    private String updatedBy;
    @JsonFormat(shape = Shape.STRING, pattern = "MM-dd-yyyy HH:mm:ss")
    private Date createdTime;
    @JsonFormat(shape = Shape.STRING, pattern = "MM-dd-yyyy HH:mm:ss")
    private Date updatedTime;
    @JsonFormat(shape = Shape.STRING, pattern = "MM-dd-yyyy HH:mm:ss")
    private Date deletedTime;
    private boolean active;
}
