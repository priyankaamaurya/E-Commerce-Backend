package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.OrderItemRequest;
import com.ecommerce.backend.dto.OrderStatusRequest;
import com.ecommerce.backend.model.Order;
import com.ecommerce.backend.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order Controller", description = "APIs for managing orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // PLACE ORDER
    @Operation(
            summary = "Place a new order",
            description = "User places an order with multiple times",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PostMapping("/place")
    public Order placeOrder(@RequestBody List<OrderItemRequest> items,
                            Authentication auth) {

        String username = auth.getName();
        return orderService.placeOrder(username, items);
    }

    // GET MY ORDERS
    @Operation(
            summary = "Get logged-in user's orders",
            description = "Returns paginated orders of current user",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping("/my")
    public Page<Order> getMyOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Authentication auth) {

        String username = auth.getName();

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("orderDate").descending()
        );

        return orderService.getUserOrders(username, pageable);
    }

    //  GET ALL ORDERS (ADMIN ONLY)
    @Operation(
            summary = " Get all orders (Admin only)",
            description = "Admin can view all orders",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // ADMIN UPDATE ORDER STATUS
    @Operation(
            summary = "Update order status",
            description = "Admin updates order status (PLACED, SHIPPED, DELIVERED)",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{orderId}/status")
    public Order updateStatus(
            @PathVariable Long orderId,
            @RequestBody OrderStatusRequest request) {

        return orderService.updateOrderStatus(orderId, request.getStatus());
    }

}

