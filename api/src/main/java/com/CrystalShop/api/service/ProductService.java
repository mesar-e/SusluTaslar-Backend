package com.CrystalShop.api.service;

import com.CrystalShop.api.dto.ProductRequest;
import com.CrystalShop.api.dto.ProductResponse;


import java.util.List;

public interface ProductService {
    List<ProductResponse> getAllProductsDto();
    ProductResponse getProductDtoById(Long id);
    ProductResponse save(ProductRequest productRequest);
    ProductResponse update(Long id, ProductRequest productRequest);
    void delete(Long id);
}
