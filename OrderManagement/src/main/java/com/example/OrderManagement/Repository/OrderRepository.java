package com.example.OrderManagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.OrderManagement.Model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
