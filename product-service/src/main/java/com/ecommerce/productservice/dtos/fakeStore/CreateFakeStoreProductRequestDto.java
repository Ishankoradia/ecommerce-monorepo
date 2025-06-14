package com.ecommerce.productservice.dtos.fakeStore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateFakeStoreProductRequestDto {
    private Long id;
    private String title;
    private Double price;
    private String description;
    private String category;
    private String image;
}
