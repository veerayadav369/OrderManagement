package com.example.OrderManagement.controller;

import com.example.OrderManagement.DTOs.OrderDTO;
import com.example.OrderManagement.Model.Order;
import com.example.OrderManagement.Service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class OrderViewController {

    private final OrderService orderService;

    public OrderViewController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public String viewOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "orders"; // orders.html
    }
    @GetMapping("/create-order")
    public String showCreateOrderForm(Model model) {
        model.addAttribute("orderDTO", new OrderDTO()); // Ensure OrderDTO is properly defined
        return "create-order"; // This should match your Thymeleaf template name
    }

    @GetMapping("/orders/view/{id}")
    public String viewOrderDetails(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        return "order-details"; // order-details.html
    }
}
