package com.ecommerce.productservice.controllers;

import com.ecommerce.productservice.exceptions.ProductNotFoundException;
import com.ecommerce.productservice.models.Product;
import com.ecommerce.productservice.services.ProductService;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductControllerTest  {

    @MockitoBean
    private ProductService productService;

    @Autowired
    private ProductController productController;

    @BeforeAll
    public static void loadEnv() {
        Dotenv dotenv = Dotenv.load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
    }

    @Test
    public void testGetSingleProduct_PositiveCase() throws ProductNotFoundException {
        Long productId = 1L;
        Product expectedProduct = new Product();
        expectedProduct.setId(productId);
        expectedProduct.setTitle("iphone 16");
        expectedProduct.setDescription("iphone 16 description");
        expectedProduct.setPrice(10000.0);

        when(productService.getSingleProduct(productId)).thenReturn(expectedProduct);

        ResponseEntity<Product> response = productController.getSingleProduct(productId);

        assertEquals(expectedProduct, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProduct.getId(), response.getBody().getId());
        assertEquals("iphone 16 description", response.getBody().getDescription());
        assertEquals("iphone 16", response.getBody().getTitle());
        assertEquals(10000.0, response.getBody().getPrice());
    }

    @Test
    public void testGetSingleProduct_ThrowsProductNotFoundException() throws ProductNotFoundException {
        ProductNotFoundException productNotFoundException = new ProductNotFoundException("Product not found", "Some more detail", -1L);

        when(productService.getSingleProduct(-1L))
                .thenThrow(productNotFoundException);

        Exception exception = assertThrows(ProductNotFoundException.class,
                () -> productController.getSingleProduct(-1L));

        assertEquals(productNotFoundException.getMessage(), exception.getMessage());
    }
}