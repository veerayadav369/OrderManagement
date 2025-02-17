package com.example.OrderManagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "index"; // Thymeleaf will look for index.html in src/main/resources/templates/
    }
}
