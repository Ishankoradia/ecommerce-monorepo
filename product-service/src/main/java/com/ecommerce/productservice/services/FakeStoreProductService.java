package com.ecommerce.productservice.services;

import com.ecommerce.productservice.dtos.CreateProductRequestDto;
import com.ecommerce.productservice.dtos.fakeStore.CreateFakeStoreProductRequestDto;
import com.ecommerce.productservice.dtos.fakeStore.FakeStoreProductDto;
import com.ecommerce.productservice.exceptions.CategoryNotFoundException;
import com.ecommerce.productservice.exceptions.ProductNotFoundException;
import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.models.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements  ProductService{
    private final RestTemplate client;


    public FakeStoreProductService(RestTemplate client) {
        this.client = client;
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponse = this.client.getForEntity(
                "https://fakestoreapi.com/products/" + productId,
                FakeStoreProductDto.class);
        FakeStoreProductDto fakeStoreProductDto = fakeStoreProductDtoResponse.getBody();

        if (fakeStoreProductDto == null) {
            throw new ProductNotFoundException(
                    "Product with id " + productId + " does not exist",
                    "Please try again with a different product id",
                    productId);
        }

        return from(fakeStoreProductDto);
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
                .map(this::from)
                .toList();
    }

    @Override
    public Product createProduct(CreateProductRequestDto createProductRequestDto) {
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponse =  this.client.postForEntity(
                "https://fakestoreapi.com/products",
                from(createProductRequestDto),
                FakeStoreProductDto.class);
        FakeStoreProductDto fakeStoreProductDto = fakeStoreProductDtoResponse.getBody();

        return from(fakeStoreProductDto);
    }


    @Override
    public Boolean deleteProduct(Long productId) {
        this.client.delete("https://fakestoreapi.com/products/" + productId);
        return true;
    }

    @Override
    public Product patchProduct(Long productId, CreateProductRequestDto createProductRequestDto) throws ProductNotFoundException, CategoryNotFoundException {
        return null;
    }

    @Override
    public Product replaceProduct(Long productId, CreateProductRequestDto createProductRequestDto) throws ProductNotFoundException, CategoryNotFoundException {
        return null;
    }

    private Product from(FakeStoreProductDto fakeStoreProductDto) {
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

    private CreateFakeStoreProductRequestDto from(CreateProductRequestDto requestDto) {
        if (requestDto == null) {
            return null;
        }
        CreateFakeStoreProductRequestDto createProductRequestDto = new CreateFakeStoreProductRequestDto();

        createProductRequestDto.setDescription(requestDto.getDescription());
        createProductRequestDto.setTitle(requestDto.getTitle());
        createProductRequestDto.setPrice(requestDto.getPrice());
        createProductRequestDto.setImage(requestDto.getImage());
        createProductRequestDto.setCategory(requestDto.getCategoryName());

        return createProductRequestDto;
    }
}
