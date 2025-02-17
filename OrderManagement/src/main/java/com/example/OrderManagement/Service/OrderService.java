package com.example.OrderManagement.Service;

import java.util.List;

import com.example.OrderManagement.DTOs.OrderDTO;
import com.example.OrderManagement.Model.Order;

public interface OrderService {
    Order createOrder(OrderDTO orderDTO);
    Order getOrderById(Long id);
    List<Order> getAllOrders();
    Order updateOrder(Long id, OrderDTO orderDTO);
    void deleteOrder(Long id);
}