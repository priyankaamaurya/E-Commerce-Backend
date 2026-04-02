package com.ecommerce.backend.service;

import com.ecommerce.backend.model.CartItem;
import com.ecommerce.backend.model.Product;
import com.ecommerce.backend.repository.CartRepository;
import com.ecommerce.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private ProductRepository productRepo;

    // Add to cart
    public CartItem addToCart(String username,Long productId, int quantity) {

        Product product = productRepo.findById(productId)
                .orElseThrow(()-> new RuntimeException("Product not found"));

        CartItem item =new CartItem();
        item.setUsername(username);
        item.setProduct(product);
        item.setQuantity(quantity);

        return cartRepo.save(item);

    }

    // Get user cart
    public List<CartItem> getCart(String username) {
        return cartRepo.findByUsername(username);
    }

    // Remove item
    public String removeItem(Long id) {
        cartRepo.deleteById(id);
        return "Item removed";
    }
}
