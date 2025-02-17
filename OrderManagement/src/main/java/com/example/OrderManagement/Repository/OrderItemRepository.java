package com.example.OrderManagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.OrderManagement.Model.OrderItem;

import jakarta.transaction.Transactional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
	 @Transactional
	    void deleteByOrderId(Long orderId);
}