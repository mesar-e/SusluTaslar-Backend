package com.CrystalShop.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderItemRequest {

    @NotNull(message = "ürün seçilmelidir")
    private Long productId;


    @NotNull(message = "Adet boş olamaz")
    @Min(value = 1, message = "en az 1 adet sipariş edilmelidir")
    private Integer quantity;
}
