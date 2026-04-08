package com.ecommerce.backend.controller;

import com.ecommerce.backend.model.Order;
import com.ecommerce.backend.model.OrderItem;
import com.ecommerce.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // PLACE ORDER
    @PostMapping("/place")
    public Order placeOrder(@RequestBody List<OrderItem> items) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return orderService.placeOrder(username, items);
    }

    // GET MY ORDERS
    @GetMapping("/my")
    public Page<Order> getMyOrders(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("orderDate").descending()
        );

        return orderService.getUserOrders(username, pageable);
    }

    // ADMIN ONLY
    @GetMapping("/all")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // ADMIN UPDATE ORDER STATUS
    @PutMapping("/{orderId}/status")
    public Order updateStatus(
            @PathVariable Long orderId,
            @RequestParam String status) {

        return orderService.updateOrderStatus(orderId,status);
    }

}

