package com.ecommerce.ecommerce_order_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecommerce_order_service.entity.OrderItemEntity;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Integer> {
    @Query("SELECT o from OrderItemEntity o where o.deletedTime is null and o.orderId=?1")
    List<OrderItemEntity> getByOrderId(int orderId);

    @Query("SELECT o from OrderItemEntity o where o.deletedTime is null and o.orderId=?1 and o.productId=?2")
    List<OrderItemEntity> findByOrderIdAndProductId(int orderId, int productId);
}
