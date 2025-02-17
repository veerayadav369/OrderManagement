package com.example.OrderManagement.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.OrderManagement.DTOs.OrderDTO;
import com.example.OrderManagement.Model.Order;
import com.example.OrderManagement.Model.OrderItem;
import com.example.OrderManagement.Model.Status;
import com.example.OrderManagement.Repository.OrderRepository;
import com.example.OrderManagement.Repository.OrderItemRepository;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    @Transactional
    public Order createOrder(OrderDTO orderDTO) {
        if (orderDTO.getItems() == null || orderDTO.getItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order must contain at least one item.");
        }

        Order order = new Order();
        order.setCustomerName(orderDTO.getCustomerName());
        order.setOrderDate(orderDTO.getOrderDate());
        order.setStatus(Status.NEW);

        // Save order first to generate an ID
        Order savedOrder = orderRepository.save(order);

        // Convert DTO items to entity items
        List<OrderItem> orderItems = orderDTO.getItems().stream().map(itemDTO -> {
            OrderItem item = new OrderItem();
            item.setProductName(itemDTO.getProductName());
            item.setQuantity(itemDTO.getQuantity());
            item.setPrice(itemDTO.getPrice());
            item.setOrder(savedOrder); // Set the relationship
            return item;
        }).collect(Collectors.toList());

        // Save order items
        orderItemRepository.saveAll(orderItems);

        // Calculate totalAmount
        double totalAmount = orderItems.stream()
                .mapToDouble(item -> item.getQuantity() * item.getPrice())
                .sum();
        savedOrder.setTotalAmount(totalAmount);

        return orderRepository.save(savedOrder);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with ID " + id + " not found."));
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional
    public Order updateOrder(Long id, OrderDTO orderDTO) {
        Order order = getOrderById(id); // Fetch the existing order

        // Update basic order details
        order.setCustomerName(orderDTO.getCustomerName());
        order.setOrderDate(orderDTO.getOrderDate());
        order.setStatus(orderDTO.getStatus());

        // Get existing order items
        List<OrderItem> existingItems = order.getItems();

        // Map DTO items to entity items
        List<OrderItem> updatedItems = orderDTO.getItems().stream().map(itemDTO -> {
            // Check if the item already exists
            OrderItem existingItem = existingItems.stream()
                .filter(item -> item.getProductName().equals(itemDTO.getProductName()))
                .findFirst()
                .orElse(null);

            if (existingItem != null) {
                // Update existing item
                existingItem.setQuantity(itemDTO.getQuantity());
                existingItem.setPrice(itemDTO.getPrice());
                return existingItem;
            } else {
                // Add new item
                OrderItem newItem = new OrderItem();
                newItem.setProductName(itemDTO.getProductName());
                newItem.setQuantity(itemDTO.getQuantity());
                newItem.setPrice(itemDTO.getPrice());
                newItem.setOrder(order);
                return newItem;
            }
        }).collect(Collectors.toList());

        // Remove items that are no longer in the updated list
        existingItems.removeIf(existingItem -> updatedItems.stream()
            .noneMatch(updatedItem -> updatedItem.getProductName().equals(existingItem.getProductName())));

        // Save updated items
        orderItemRepository.saveAll(updatedItems);

        // Update the order's item list
        order.setItems(updatedItems);

        // Recalculate total amount
        double totalAmount = updatedItems.stream()
            .mapToDouble(item -> item.getQuantity() * item.getPrice())
            .sum();
        order.setTotalAmount(totalAmount);

        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with ID " + id + " not found.");
        }
        // First delete order items before deleting the order
        orderItemRepository.deleteByOrderId(id);
        orderRepository.deleteById(id);
    }
}
