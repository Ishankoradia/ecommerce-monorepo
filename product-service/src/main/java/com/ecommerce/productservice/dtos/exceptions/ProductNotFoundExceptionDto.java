package com.ecommerce.productservice.dtos.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductNotFoundExceptionDto {
    private Long productId;
    private String message;
    private String details;
}