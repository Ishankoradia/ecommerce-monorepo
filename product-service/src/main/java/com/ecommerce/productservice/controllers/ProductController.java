package com.ecommerce.productservice.controllers;

import com.ecommerce.productservice.dtos.CreateProductRequestDto;
import com.ecommerce.productservice.exceptions.CategoryNotFoundException;
import com.ecommerce.productservice.exceptions.ProductNotFoundException;
import com.ecommerce.productservice.models.Product;
import com.ecommerce.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    //@Qualifier("dbProductService")
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getSingleProduct(@PathVariable("id") Long productId) throws ProductNotFoundException {
        return new ResponseEntity<>(
                this.productService.getSingleProduct(productId),
                HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(
                this.productService.getAllProducts(),
                HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequestDto payload) throws CategoryNotFoundException, ProductNotFoundException {
        return new ResponseEntity<>(
                this.productService.createProduct(payload),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable("id") Long productId){
        return new ResponseEntity<>(
                this.productService.deleteProduct(productId),
                HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long productId, @RequestBody CreateProductRequestDto payload) throws CategoryNotFoundException, ProductNotFoundException {
        return new ResponseEntity<>(
                this.productService.patchProduct(productId, payload),
                HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> replaceProduct(@PathVariable("id") Long productId, @RequestBody CreateProductRequestDto payload) throws CategoryNotFoundException, ProductNotFoundException {
        return new ResponseEntity<>(
                this.productService.replaceProduct(productId, payload),
                HttpStatus.OK);
    }
}
