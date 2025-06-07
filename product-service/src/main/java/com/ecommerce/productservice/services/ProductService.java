package com.ecommerce.productservice.services;

import com.ecommerce.productservice.dtos.CreateProductRequestDto;
import com.ecommerce.productservice.exceptions.CategoryNotFoundException;
import com.ecommerce.productservice.exceptions.ProductNotFoundException;
import com.ecommerce.productservice.models.Product;

import java.util.List;

public interface ProductService {
    Product getSingleProduct(Long productId) throws ProductNotFoundException;

    List<Product> getAllProducts();

    Product createProduct(CreateProductRequestDto createProductRequestDto) throws CategoryNotFoundException, ProductNotFoundException;

    Boolean deleteProduct(Long productId);

    Product patchProduct(Long productId, CreateProductRequestDto createProductRequestDto) throws ProductNotFoundException, CategoryNotFoundException;

    Product replaceProduct(Long productId, CreateProductRequestDto createProductRequestDto) throws ProductNotFoundException, CategoryNotFoundException;
}
