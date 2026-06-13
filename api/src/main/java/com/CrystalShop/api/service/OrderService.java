package com.CrystalShop.api.service;

import com.CrystalShop.api.dto.OrderItemRequest;
import com.CrystalShop.api.dto.OrderRequest;
import com.CrystalShop.api.dto.OrderResponse;
import com.CrystalShop.api.entity.Order;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest);
    List<OrderResponse> getMyOrders();
    List<OrderResponse> getAllOrders();
    OrderResponse cancelOrder(Long id);
}
