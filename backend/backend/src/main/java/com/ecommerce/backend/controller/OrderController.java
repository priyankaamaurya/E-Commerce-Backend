package com.ecommerce.backend.controller;

import com.ecommerce.backend.model.Order;
import com.ecommerce.backend.model.OrderItem;
import com.ecommerce.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // ✅ PLACE ORDER
    @PostMapping("/place")
    public Order placeOrder(@RequestBody List<OrderItem> items) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return orderService.placeOrder(username, items);
    }

    // ✅ GET MY ORDERS
    @GetMapping("/my")
    public List<Order> getMyOrders() {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return orderService.getUserOrders(username);
    }

    // ✅ ADMIN ONLY
    @GetMapping("/all")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
}