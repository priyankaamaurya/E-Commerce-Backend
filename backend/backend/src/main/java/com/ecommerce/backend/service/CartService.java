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

    // ADD TO CART (with duplicate handling)
    public CartItem addToCart(String username,Long productId, Integer quantity) {

        if (quantity <= 0){
            throw new RuntimeException("Quantity must be greater than 0");
        }

        Product product = productRepo.findById(productId)
                .orElseThrow(()-> new RuntimeException("Product not found"));

        // CHECK if item already exists
        CartItem existingItem = cartRepo
                .findByUsernameAndProductId(username, productId)
                .orElse(null);

        if (existingItem != null){
            // UPDATE quantity instead of creating new
            existingItem.setQuantity(existingItem.getQuantity()+ quantity);
            return cartRepo.save(existingItem);
        }

        // CREATE new item
        CartItem item =new CartItem();
        item.setUsername(username);
        item.setProduct(product);
        item.setQuantity(quantity);

        return cartRepo.save(item);

    }

    // GET USER CART
    public List<CartItem> getCart(String username) {
        return cartRepo.findByUsername(username);
    }

    // UPDATE QUANTITY
    public CartItem updateQuantity(Long itemId, Integer quantity) {

        if (quantity <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }

        CartItem item = cartRepo.findById(itemId)
                .orElseThrow(()-> new RuntimeException("Item not found"));

        item.setQuantity(quantity);
        return cartRepo.save(item);

    }

    // REMOVE SINGLE ITEM
    public String removeItem(Long itemId) {
        cartRepo.deleteById(itemId);
        return "Item removed from cart";
    }

    // CLEAR CART
    public String clearCart(String username) {
        List<CartItem> items = cartRepo.findByUsername(username);
        cartRepo.deleteAll(items);
        return "Cart cleared";
    }




}
