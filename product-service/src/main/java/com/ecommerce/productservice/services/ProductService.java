package com.ecommerce.productservice.services;

import com.ecommerce.productservice.dtos.CreateProductRequestDto;
import com.ecommerce.productservice.exceptions.ProductNotFoundException;
import com.ecommerce.productservice.models.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    Product getSingleProduct(Long productId) throws ProductNotFoundException;

    List<Product> getAllProducts();

    Product createProduct(CreateProductRequestDto createProductRequestDto);

    ResponseEntity<Void> deleteProduct(Long productId);
}
