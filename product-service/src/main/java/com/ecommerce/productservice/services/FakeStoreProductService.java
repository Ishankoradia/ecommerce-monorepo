package com.ecommerce.productservice.services;

import com.ecommerce.productservice.dtos.CreateProductRequestDto;
import com.ecommerce.productservice.dtos.FakeStoreProductDto;
import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.models.Product;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FakeStoreProductService implements  ProductService{
    private RestTemplate client;

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
        ParameterizedTypeReference<List<FakeStoreProductDto>> responseType = new ParameterizedTypeReference<>() {};

        ResponseEntity<List<FakeStoreProductDto>> fakeStoreProductDtoResponse = this.client.exchange(
                "https://fakestoreapi.com/products",
                HttpMethod.GET,
                null,
                responseType);
        List<FakeStoreProductDto> fakeStoreProductsDto = fakeStoreProductDtoResponse.getBody();

        if (fakeStoreProductsDto == null || fakeStoreProductsDto.isEmpty()) {
            return null;
        }

        return fakeStoreProductsDto.stream()
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
    public boolean deleteProduct(Long productId) {
        return false;
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
