package com.example.OrderManagement.DTOs;

import java.time.LocalDate;
import java.util.List;
import com.example.OrderManagement.Model.Status;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

public class OrderDTO {
    
    private Long id;  // ✅ Add this field for update functionality

    @NotNull(message = "Customer name cannot be null")
    @Size(min = 3, message = "Customer name must be at least 3 characters long")
    private String customerName;
    
    @NotNull(message = "Order date cannot be null")
    @PastOrPresent(message = "Order date cannot be in the future")
    private LocalDate orderDate;
    
    @NotNull(message = "Order items cannot be null")
    @Size(min = 1, message = "Order must contain at least one item")
    private List<OrderItemDTO> items;
    
    @NotNull(message = "Status cannot be null")
    private Status status;

    // ✅ Default Constructor with Default Status
    public OrderDTO() {
        this.status = Status.NEW;  // Default status set to NEW
    }

    // ✅ Constructor with Parameters
    public OrderDTO(Long id, String customerName, LocalDate orderDate, List<OrderItemDTO> items, Status status) {
        this.id = id;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.items = items;
        this.status = (status != null) ? status : Status.NEW; // Set default if null
    }

    // ✅ Getters and Setters
    public Long getId() {  // ✅ Getter for id
        return id;
    }

    public void setId(Long id) {  // ✅ Setter for id
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    // ✅ Override toString() for Debugging
    @Override
    public String toString() {
        return "OrderDTO [id=" + id + ", customerName=" + customerName + ", orderDate=" + orderDate + 
               ", items=" + items + ", status=" + status + "]";
    }
}
