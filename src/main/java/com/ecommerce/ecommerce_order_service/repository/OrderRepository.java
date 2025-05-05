package com.ecommerce.ecommerce_order_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecommerce_order_service.entity.OrderEntity;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
    @Query("SELECT o from OrderEntity o where o.deletedTime is null")
    List<OrderEntity> findAllOrder();

    @Query("SELECT o from OrderEntity o where o.deletedTime is null and id=?1")
    Optional<OrderEntity> findById(int id);
}
