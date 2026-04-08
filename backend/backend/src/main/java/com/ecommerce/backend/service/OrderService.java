package com.ecommerce.backend.service;

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
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository repo;

    @Autowired
    private EmailService emailService;

    public Order placeOrder(String username, List<OrderItem> items) {

        Order order = new Order();

        order.setUsername(username);
        order.setOrderDate(LocalDateTime.now());

        order.setStatus("PENDING");

        Double total = 0.0;

        for (OrderItem item : items) {

            // FETCH PRODUCT FROM DB
            Product product = repo.findById(item.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            // IMPORTANT LINE (YOU MISSED THIS)
            item.setProduct(product);

            // SET VALUES
            item.setProductName(product.getName());
            item.setPrice(product.getPrice());

            // CALCULATE TOTAL
            total += product.getPrice() * item.getQuantity();

            // LINK ORDER
            item.setOrder(order);
        }

        order.setItems(items);
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
