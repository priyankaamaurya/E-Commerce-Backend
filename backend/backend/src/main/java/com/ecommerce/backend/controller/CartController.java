package com.ecommerce.backend.controller;

import com.ecommerce.backend.model.CartItem;
import com.ecommerce.backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService service;

    // Add to cart
    @PostMapping("/add")
    public CartItem addToCart(@RequestParam Long productId, @RequestParam int quantity) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return service.addToCart(username, productId, quantity);
    }

    // View cart
    @GetMapping
    public List<CartItem> getCart() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return service.getCart(username);
    }

    //Remove item
    @DeleteMapping("/{id}")
    public String removeItem(@PathVariable Long id) {
        return service.removeItem(id);
    }
}
