package com.ecommerce.productservice.services;

import com.ecommerce.productservice.dtos.CreateProductRequestDto;
import com.ecommerce.productservice.dtos.FakeStoreProductDto;
import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.models.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FakeStoreProductService implements  ProductService{
    private final RestTemplate client;

    public FakeStoreProductService(RestTemplate client) {
        this.client = client;
    }

    @Override
    public Product getSingleProduct(Long productId){

        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponse = this.client.getForEntity(
                "https://fakestoreapi.com/products/" + productId,
                FakeStoreProductDto.class);
        FakeStoreProductDto fakeStoreProductDto = fakeStoreProductDtoResponse.getBody();

        return convertFakeStoreProductDtoToProduct(fakeStoreProductDto);
    }

    @Override
    public List<Product> getAllProducts() {
        ResponseEntity<FakeStoreProductDto[]> fakeStoreProductDtoResponse = this.client.getForEntity(
                "https://fakestoreapi.com/products",
                FakeStoreProductDto[].class);
        FakeStoreProductDto[] fakeStoreProductsDto = fakeStoreProductDtoResponse.getBody();

        if (fakeStoreProductsDto == null) {
            return new ArrayList<>();
        }

        return Arrays.stream(fakeStoreProductsDto)
                .map(FakeStoreProductService::convertFakeStoreProductDtoToProduct)
                .toList();
    }

    @Override
    public Product createProduct(CreateProductRequestDto createProductRequestDto) {
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponse =  this.client.postForEntity(
                "https://fakestoreapi.com/products",
                createProductRequestDto,
                FakeStoreProductDto.class);
        FakeStoreProductDto fakeStoreProductDto = fakeStoreProductDtoResponse.getBody();

        return convertFakeStoreProductDtoToProduct(fakeStoreProductDto);
    }

    @Override
    public ResponseEntity<Void> deleteProduct(Long productId) {
        this.client.delete("https://fakestoreapi.com/products/" + productId);
        return ResponseEntity.ok().build();
    }

    public static Product convertFakeStoreProductDtoToProduct(FakeStoreProductDto fakeStoreProductDto) {
        if (fakeStoreProductDto == null) {
            return null;
        }

        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setImage(fakeStoreProductDto.getImage());

        Category category = new Category();
        category.setTitle(fakeStoreProductDto.getCategory());
        product.setCategory(category);

        return product;
    }
}
