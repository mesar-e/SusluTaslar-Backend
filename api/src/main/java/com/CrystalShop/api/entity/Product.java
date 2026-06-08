package com.CrystalShop.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "name")
    private String name;


    @Size( max = 500)
    @Column(name = "description")
    private  String description;

    @NotNull
    @Positive
    @Column(name = "price")
    private BigDecimal price;

    @NotNull
    @Min(value = 0)
    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "category_id")
    private Category category;
}
