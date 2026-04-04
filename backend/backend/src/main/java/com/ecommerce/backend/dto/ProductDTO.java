package com.ecommerce.backend.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @Positive(message = "Price must be positive")
    private Double price;

    @NotBlank(message = "Description is required")
    private String description;

    @Min(value = 0, message = "Stock cannot be negative")
    private Integer stock;
}
