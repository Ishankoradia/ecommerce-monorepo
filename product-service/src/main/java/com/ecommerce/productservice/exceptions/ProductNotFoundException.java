package com.ecommerce.productservice.exceptions;

import lombok.Getter;

@Getter
public class ProductNotFoundException extends BaseException {
    private final Long productId;

    public ProductNotFoundException(String message, String details, Long productId) {
        super(message, details);
        this.productId = productId;
    }
}
