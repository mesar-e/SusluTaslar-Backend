package com.CrystalShop.api.service;

import com.CrystalShop.api.dto.ProductRequest;
import com.CrystalShop.api.dto.ProductResponse;
import com.CrystalShop.api.entity.Category;
import com.CrystalShop.api.entity.Product;
import com.CrystalShop.api.exception.ApiException;
import com.CrystalShop.api.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }


    private Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ApiException("Ürün bulunamadı! ID: " + id, HttpStatus.NOT_FOUND));
    }

    //helper fonksiyon
    private ProductResponse convertToResponse(Product product){
        ProductResponse response= new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());

        if (product.getCategory() != null) {
            response.setCategoryId(product.getCategory().getId());
            response.setCategoryName(product.getCategory().getName());
        }
        return response;
    }
    @Override
    public List<ProductResponse> getAllProductsDto() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductDtoById(Long id) {
        Product product = findById(id);
        return convertToResponse(product);
    }

    @Override
    public ProductResponse save(ProductRequest productRequest) {
        Category category = categoryService.findById(productRequest.getCategoryId());

        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);
        return convertToResponse(savedProduct);
    }

    @Override
    public ProductResponse update(Long id, ProductRequest productRequest) {
        Product existingProduct = findById(id);
        Category category = categoryService.findById(productRequest.getCategoryId());

        existingProduct.setName(productRequest.getName());
        existingProduct.setDescription(productRequest.getDescription());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setCategory(category);

        Product updatedProduct = productRepository.save(existingProduct);
        return convertToResponse(updatedProduct);
    }

    @Override
    public void delete(Long id) {
        Product product = findById(id);
        productRepository.delete(product);
    }
}
