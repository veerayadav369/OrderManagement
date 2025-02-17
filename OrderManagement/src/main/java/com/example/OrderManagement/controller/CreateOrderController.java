package com.example.OrderManagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.OrderManagement.DTOs.OrderDTO;
import com.example.OrderManagement.Model.Order;
import com.example.OrderManagement.Model.Status;  // Import the Status enum
import com.example.OrderManagement.Service.OrderService;

@Controller
@RequestMapping("/orders")
public class CreateOrderController {  // Fixed Typo
    private final OrderService orderService;

    public CreateOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/create")
    public String showCreateOrderForm(Model model) {
        model.addAttribute("orderDTO", new OrderDTO());
        return "create-order";  // Ensure this matches your Thymeleaf template name
    }

    @PostMapping("/create")  // Ensure the form action matches this URL
    public String createOrder(@ModelAttribute OrderDTO orderDTO) {
        // Set a default status if not provided
        if (orderDTO.getStatus() == null) {
            orderDTO.setStatus(Status.NEW);  // Corrected: Use Enum instead of String
        }

        orderService.createOrder(orderDTO);
        return "redirect:/orders";
    }
    
    // Show Edit Order Form
    @GetMapping("/edit/{id}")
    public String showEditOrderForm(@PathVariable Long id, Model model) {
        Order orderDTO = orderService.getOrderById(id);
        model.addAttribute("orderDTO", orderDTO);
        return "edit-order"; 
    }

    @PostMapping("/update")
    public String updateOrder(@ModelAttribute OrderDTO orderDTO) {
        orderService.updateOrder(orderDTO.getId(), orderDTO); // Pass the ID as well
        return "redirect:/orders"; // Redirect back to the order list
    }
    
    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/orders";  // Redirect back to the order list after deletion
    }
}
