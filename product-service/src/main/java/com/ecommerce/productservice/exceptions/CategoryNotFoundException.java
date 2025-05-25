package com.ecommerce.productservice.exceptions;

public class CategoryNotFoundException extends BaseException {
    public CategoryNotFoundException(String message, String details) {
        super(message, details);
    }
}
