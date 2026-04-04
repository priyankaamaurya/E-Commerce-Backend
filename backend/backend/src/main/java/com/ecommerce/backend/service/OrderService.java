package com.ecommerce.backend.service;

import com.ecommerce.backend.model.Order;
import com.ecommerce.backend.model.OrderItem;
import com.ecommerce.backend.model.Product;
import com.ecommerce.backend.repository.OrderRepository;
import com.ecommerce.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository repo;

    public Order placeOrder(String username, List<OrderItem> items) {

        Order order = new Order();

        order.setUsername(username);
        order.setOrderDate(new Date());

        Double total = 0.0;

        for (OrderItem item : items) {

            // FETCH PRODUCT FROM DB
            Product product = repo.findById(item.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

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

        return orderRepository.save(order);
    }

    public List<Order> getUserOrders(String username) {
        return orderRepository.findByUsername(username);
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }
}
