package com.CrystalShop.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id")
private Long id;

@Column(name = "name")
private String name;

@Column(name = "description")
private  String description;

@Column(name = "price")
private BigDecimal price;

@Column(name = "stock_quantity")
private Integer stockQuantity;

@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
@JoinColumn(name = "category_id")
private Category category;
}
