package com.ecommerce.backend.dto;

import lombok.Data;

@Data
public class OrderItemDTO {

    private Integer productId;
    private Integer quantity;
}
