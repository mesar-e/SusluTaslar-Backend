package com.CrystalShop.api.service;

import com.CrystalShop.api.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category findById(Long id);
    Category save(Category category);
    void delete(Category category);
}
