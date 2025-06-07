package com.ecommerce.productservice.services;

import com.ecommerce.productservice.ProductServiceApplication;
import com.ecommerce.productservice.dtos.CreateProductRequestDto;
import com.ecommerce.productservice.exceptions.CategoryNotFoundException;
import com.ecommerce.productservice.exceptions.ProductNotFoundException;
import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.models.Product;
import com.ecommerce.productservice.repositories.CategoryRepository;
import com.ecommerce.productservice.repositories.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("dbProductService")
//@Primary
public class DbProductService implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public DbProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        return this.productRepository.
                findById(productId).
                orElseThrow(() ->
                        new ProductNotFoundException
                                ("Product not found", "Please try with a different id", productId)
                );
    }

    @Override
    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    @Override
    public Product createProduct(Product product) throws CategoryNotFoundException {
        Category category = product.getCategory();

        if (category == null) {
            throw new CategoryNotFoundException("Product cant' be created without category" , "Please select a category");
        }

        // Check for category with title
        Optional<Category> categoryOptional = categoryRepository.findByTitle(category.getTitle());
        if (categoryOptional.isEmpty()) {
            category = categoryRepository.save(category);
        } else {
            category = categoryOptional.get();
        }
        product.setCategory(category);

        return productRepository.save(product);
    }

    @Override
    public ResponseEntity<Void> deleteProduct(Long productId) {
        this.productRepository.deleteById(productId);
        return ResponseEntity.ok().build();
    }
}
