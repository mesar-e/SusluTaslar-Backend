package com.CrystalShop.api.entity;

import com.CrystalShop.api.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();


    private BigDecimal totalPrice;


    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;


    private LocalDateTime orderDate = LocalDateTime.now();
}