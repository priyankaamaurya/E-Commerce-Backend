package com.ecommerce.backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderRequestDTO {

    private String username;
    private List<OrderItemDTO> items;

}
