package com.CrystalShop.api.dto;

import lombok.Data;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private double price;

    private Long categoryId;
    private String categoryName;
}
