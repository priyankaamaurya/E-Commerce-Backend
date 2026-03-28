package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.ProductDTO;
import com.ecommerce.backend.exception.ResourceNotFoundException;
import com.ecommerce.backend.model.Product;
import com.ecommerce.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;

    public Product addProduct(ProductDTO dto) {
        Product product = new Product();

        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setStock(dto.getStock());

        return repo.save(product);
    }

    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    public Product getProductById(Long id){
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id ));
    }

    public String deleteProduct(Long id){
        Product product = repo.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        repo.delete(product);
        return "Product deleted successfully";
    }

    public Product updateProduct(Long id, ProductDTO dto){
        Product existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

            existing.setName(dto.getName());
            existing.setPrice(dto.getPrice());
            existing.setDescription(dto.getDescription());
            existing.setStock(dto.getStock());

            return repo.save(existing);
    }
}
