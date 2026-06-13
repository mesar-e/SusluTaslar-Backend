package com.CrystalShop.api.dto;

import com.CrystalShop.api.enums.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Data
public class OrderResponse {
    private Long id;
    private Long userId;
    private BigDecimal totalPrice;
    private OrderStatus status;
    private LocalDateTime orderDate;
    private List<OrderItemResponse> items;
}
