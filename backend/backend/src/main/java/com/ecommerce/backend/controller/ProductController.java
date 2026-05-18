package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.ProductDTO;
import com.ecommerce.backend.model.Product;
import com.ecommerce.backend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Product APIs", description = "Operations related to products")
@RestController
@RequestMapping("/api/products")
@SecurityRequirement(name = "bearerAuth")
public class ProductController {

    @Autowired
    private ProductService service ;

    // Add product
    @Operation(summary = "Add new product")
    @PostMapping
    public Product addProduct(@Valid @RequestBody ProductDTO dto) {
        return service.addProduct(dto);
    }

    // Get all products
    @Operation(summary = "Get all products")
    @GetMapping
    public List<Product> getAllProducts(){
        return service.getAllProducts();
    }

    @Operation(summary = "Get product by ID")
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id){
        return service.getProductById(id);
    }

    @Operation(summary = "Delete Product")
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id){
        return service.deleteProduct(id);
    }

    @Operation(summary = "Update product")
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO dto){
        return service.updateProduct(id, dto);
    }
}
