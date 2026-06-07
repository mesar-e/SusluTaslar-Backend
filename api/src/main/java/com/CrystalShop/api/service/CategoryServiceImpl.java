package com.CrystalShop.api.service;

import com.CrystalShop.api.entity.Category;
import com.CrystalShop.api.exception.ApiException;
import com.CrystalShop.api.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
         Optional<Category> optionalCategory =categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            return optionalCategory.get();
        }
        throw new ApiException("Category is not found with id " + id, HttpStatus.NOT_FOUND);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Category category) {
        categoryRepository.delete(category);
    }
}
