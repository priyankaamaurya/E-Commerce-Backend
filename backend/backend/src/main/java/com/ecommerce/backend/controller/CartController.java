package com.ecommerce.backend.controller;

import com.ecommerce.backend.model.CartItem;
import com.ecommerce.backend.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@Tag(name = "Cart APIs", description = "APIs for managing user shopping cart")
public class CartController {

    @Autowired
    private CartService service;

    // ADD TO CART
    @Operation(
            summary = "Add product to cart",
            description = "Adds a product to the logged-in user's cart. If product already exists, quantity is updated.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PostMapping("/add")
    public CartItem addToCart(@RequestParam Long productId,
                              @RequestParam Integer quantity,
                              Authentication auth) {

        String username = auth.getName();
        return service.addToCart(username, productId, quantity);
    }

    // VIEW CART
    @Operation(
            summary = "Get user cart",
            description = "Fetch all cart items of the logged-in user",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping
    public List<CartItem> getCart(Authentication auth) {

        String username = auth.getName();
        return service.getCart(username);
    }

    // UPDATE QUANTITY
    @Operation(
            summary = "Update cart item quantity",
            description = "Update quantity of a specific cart item",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PutMapping("/update")
    public CartItem updateCart(@RequestParam Long itemId,
                               @RequestParam Integer quantity) {

        return service.updateQuantity(itemId, quantity);
    }

    // REMOVE ITEM
    @Operation(
            summary = "Remove item from cart",
            description = "Deletes a specific item from cart using item ID",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @DeleteMapping("/{itemId}")
    public String removeItem(@PathVariable Long itemId) {
        return service.removeItem(itemId);
    }

    // CLEAR CART
    @Operation(
            summary = "Clear cart",
            description = "Removes all items from the logged-in user's cart",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @DeleteMapping("/clear")
    public String clearCart(Authentication auth) {

        String username = auth.getName();
        return service.clearCart(username);
    }

}
