package com.CrystalShop.api.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;

    private Long categoryId;
    private String categoryName;
}
