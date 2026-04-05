package com.ecommerce.backend.repository;

import com.ecommerce.backend.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // old (Optional)
    // List<Order> findByUsername(String username);

    // New (with Pagination)
    Page<Order> findByUsername (String username, Pageable pageable);
}
