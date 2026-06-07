package com.CrystalShop.api.service;

import com.CrystalShop.api.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    Product findById(Long id);
    Product save (Product product);
    void delete (Product product);
}
