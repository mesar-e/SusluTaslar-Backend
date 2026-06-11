package com.CrystalShop.api.service;

import com.CrystalShop.api.dto.CategoryResponse;
import com.CrystalShop.api.dto.CategoryRequest;
import com.CrystalShop.api.entity.Category;

import java.util.List;

public interface CategoryService {

    List<CategoryResponse> getAllCategoriesDto();
    CategoryResponse getCategoryDtoById(Long id);
    Category findById(Long id);
    CategoryResponse save(CategoryRequest categoryRequest);
    CategoryResponse update(Long id, CategoryRequest categoryRequest);
    void delete(Long id);
}
