package com.ecommerce.productservice.exceptions;

import lombok.Getter;

@Getter
public class ProductNotFoundException extends BaseException {
    private Long productId = null;

    public ProductNotFoundException(String message, String details) {
        super(message, details);
    }

    public ProductNotFoundException(String message, String details, Long productId) {
        super(message, details);
        this.productId = productId;
    }

    public ProductNotFoundException(String message, Long productId) {
        super(message, "Please provide a valid product id");
        this.productId = productId;
    }
}
