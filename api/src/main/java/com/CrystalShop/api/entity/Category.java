package com.CrystalShop.api.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Size(min = 2, max = 50)
    @Column(name = "name")
    private String name;

    @Size(max = 250)
    @Column(name = "description")
    private String description;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Product> products;
}
