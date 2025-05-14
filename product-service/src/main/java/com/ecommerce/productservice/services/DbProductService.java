package com.ecommerce.productservice.services;

import com.ecommerce.productservice.dtos.CreateProductRequestDto;
import com.ecommerce.productservice.exceptions.ProductNotFoundException;
import com.ecommerce.productservice.models.Product;
import com.ecommerce.productservice.repositories.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("dbProductService")
//@Primary
public class DbProductService implements ProductService {
    private final ProductRepository productRepository;

    public DbProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public Product createProduct(CreateProductRequestDto createProductRequestDto) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteProduct(Long productId) {
        return null;
    }
}
