package com.CrystalShop.api.repository;

import com.CrystalShop.api.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository  extends JpaRepository<OrderItem,Long> {
}
