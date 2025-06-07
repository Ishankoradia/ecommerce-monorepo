package com.ecommerce.productservice.controllers;

import com.ecommerce.productservice.models.Product;
import com.ecommerce.productservice.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = ProductController.class)
public class ProductControllerMockMVCTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllProductsAPI() throws Exception {
        Product p1 = new Product();
        p1.setId(1L);
        p1.setTitle("Product 1");
        p1.setDescription("Product 1");
        p1.setPrice(10000.0);

        Product p2 = new Product();
        p2.setId(2L);
        p2.setTitle("Product 2");
        p2.setDescription("Product 2");
        p2.setPrice(20000.0);

        List<Product> products = new ArrayList<>();
        products.add(p1);
        products.add(p2);

        when(productService.getAllProducts()).thenReturn(products);

        String expectedJson = objectMapper.writeValueAsString(products);

        mockMvc.perform(
                        MockMvcRequestBuilders.get(
                                "/products/"
                        )
                )
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }
}
