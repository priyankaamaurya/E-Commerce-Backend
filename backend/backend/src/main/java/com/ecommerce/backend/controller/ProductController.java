package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.ProductDTO;
import com.ecommerce.backend.model.Product;
import com.ecommerce.backend.repository.ProductRepository;
import com.ecommerce.backend.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService service ;

    // Add product
    @PostMapping
    public Product addProduct(@Valid @RequestBody ProductDTO dto) {
        return service.addProduct(dto);
    }

    // Get all products
    @GetMapping
    public List<Product> getAllProducts(){
        return service.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id){
        return service.getProductById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id){
        return service.deleteProduct(id);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO dto){
        return service.updateProduct(id, dto);
    }
}
