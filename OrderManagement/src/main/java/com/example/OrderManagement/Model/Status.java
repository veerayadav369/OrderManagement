package com.example.OrderManagement.Model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Status {
    NEW, CANCELED, COMPLETED;

    @JsonCreator
    public static Status fromString(String value) {
        switch (value.toLowerCase()) {
            case "pending":
                return Status.NEW; // Map "pending" to NEW
            case "cancelled":
                return Status.CANCELED; // Map to CANCELED
            case "completed":
                return Status.COMPLETED; // Ensure "Completed" maps correctly
            default:
                return Status.valueOf(value.toUpperCase());
        }
    }
}
