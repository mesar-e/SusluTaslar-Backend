package com.CrystalShop.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRequest {

    @NotBlank(message = "ürün adı boş olamaz")
    private String name;

    private String description;

    @NotNull(message = "fiyat boş olamaz")
    @Min(value = 0, message = "fiyat sıfırdan küçük olamaz")
    private Double price;

    @NotNull(message = "kategorisi boş olamaz")
    private Long categoryId;
}
