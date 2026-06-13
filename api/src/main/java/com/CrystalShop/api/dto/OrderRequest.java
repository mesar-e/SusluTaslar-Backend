package com.CrystalShop.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {

    @NotEmpty(message = "sepet boş olamaz")
    @Valid
    private List<OrderItemRequest> items;

}
