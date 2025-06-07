package com.ecommerce.productservice.services;

import com.ecommerce.productservice.dtos.CreateProductRequestDto;
import com.ecommerce.productservice.exceptions.CategoryNotFoundException;
import com.ecommerce.productservice.exceptions.ProductNotFoundException;
import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.models.Product;
import com.ecommerce.productservice.repositories.CategoryRepository;
import com.ecommerce.productservice.repositories.ProductRepository;
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
    public Product createProduct(CreateProductRequestDto createProductRequestDto) throws CategoryNotFoundException, ProductNotFoundException {
        Product product = from(createProductRequestDto, null);

        return productRepository.save(product);
    }

    @Override
    public Boolean deleteProduct(Long productId) {
        this.productRepository.deleteById(productId);
        return true;
    }

    @Override
    public Product patchProduct(Long productId, CreateProductRequestDto patchProductRequestDto) throws ProductNotFoundException, CategoryNotFoundException {
        return from(patchProductRequestDto, productId);
    }

    @Override
    public Product replaceProduct(Long productId, CreateProductRequestDto createProductRequestDto) throws ProductNotFoundException, CategoryNotFoundException {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException("Product not found", "Please try with a different id", productId);
        }
        productRepository.delete(optionalProduct.get());

        return from(createProductRequestDto, null);
    }


    private Product from(CreateProductRequestDto requestDto, Long productId) throws CategoryNotFoundException, ProductNotFoundException {
        if (requestDto.getCategoryName() == null) {
            throw new CategoryNotFoundException("Product cant' be created without category" , "Please select a category");
        }

        Category category;
        Optional<Category> categoryOptional = categoryRepository.findByTitleIgnoreCase(requestDto.getCategoryName());
        if (categoryOptional.isEmpty()) {
            category = new Category();
            category.setTitle(requestDto.getCategoryName());
            category = categoryRepository.save(category);
        } else {
            category = categoryOptional.get();
        }

        Product product;
        if (productId != null) {
            Optional<Product> optionalProduct = productRepository.findById(productId);
            if (optionalProduct.isPresent()) {
                product = optionalProduct.get();
            } else {
                throw new ProductNotFoundException("Product not found", productId);
            }
        } else {
            product = new Product();
        }

        product.setTitle(requestDto.getTitle());
        product.setDescription(requestDto.getDescription());
        product.setPrice(requestDto.getPrice());
        product.setImage(requestDto.getImage());
        product.setCategory(category);

        product = productRepository.save(product);

        return product;

    }


}
