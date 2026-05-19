package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.OrderItemRequest;
import com.ecommerce.backend.model.Order;
import com.ecommerce.backend.model.OrderItem;
import com.ecommerce.backend.model.Product;
import com.ecommerce.backend.repository.OrderRepository;
import com.ecommerce.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository repo;

    @Autowired
    private EmailService emailService;

    public Order placeOrder(String username, List<OrderItemRequest> items) {

        Order order = new Order();
        order.setUsername(username);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");

        Double total = 0.0;

        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequest req : items) {

            // FETCH PRODUCT FROM DB
            Product product = repo.findById(req.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            // CREATE NEW ORDER ITEM
            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setProductName(product.getName());
            item.setPrice(product.getPrice());
            item.setQuantity(req.getQuantity());

            // CALCULATE TOTAL
            total += product.getPrice() * req.getQuantity();

            // LINK ORDER
            item.setOrder(order);

            orderItems.add(item);
        }

        order.setItems(orderItems);
        order.setTotalPrice(total);

        // SAVE ORDER
        Order savedOrder = orderRepository.save(order);

        // SEND EMAIL
        emailService.sendOrderConfirmation(
                "prachii8826@gmail.com",
                username,
                savedOrder.getTotalPrice(),
                savedOrder.getId()
        );

        return savedOrder;
    }

    public Page<Order> getUserOrders(String username, Pageable pageable) {
        return orderRepository.findByUsername(username, pageable);
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public Order updateOrderStatus(Long orderId, String status ) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);

        return orderRepository.save(order);
    }
}
