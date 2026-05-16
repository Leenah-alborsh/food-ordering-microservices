package com.foodapp.ordering_service.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class MenuItemResponse {
    private Long id;
    private String name;
    private String category;
    private BigDecimal price;
    private Boolean available;
}
