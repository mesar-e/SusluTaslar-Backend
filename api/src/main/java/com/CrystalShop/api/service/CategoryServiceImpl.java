package com.CrystalShop.api.service;

import com.CrystalShop.api.dto.CategoryRequest;
import com.CrystalShop.api.dto.CategoryResponse;
import com.CrystalShop.api.entity.Category;
import com.CrystalShop.api.exception.ApiException;
import com.CrystalShop.api.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public List<CategoryResponse> getAllCategoriesDto(){
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category -> {
            CategoryResponse response = new CategoryResponse();
            response.setId(category.getId());
            response.setName(category.getName());
            return response;
        }).collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getCategoryDtoById(Long id) {
        Category category = findById(id);
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        return response;
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ApiException("Kategori bulunamadı! ID: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public CategoryResponse save (CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());

        Category savedCategory = categoryRepository.save(category);

        CategoryResponse response = new CategoryResponse();
        response.setName(savedCategory.getName());
        response.setId(savedCategory.getId());
        return response;
    }

    @Override
    public CategoryResponse update  (Long id, CategoryRequest categoryRequest) {
        Category existingCategory = findById(id);
        existingCategory.setName(categoryRequest.getName());

         Category updatedCAtegory = categoryRepository.save(existingCategory);

         CategoryResponse response = new CategoryResponse();
         response.setId(updatedCAtegory.getId());
         response.setName(updatedCAtegory.getName());
         return response;
    }


    @Override
    public void delete(Long id) {
        Category category = findById(id);
        categoryRepository.delete(category);
    }
}
