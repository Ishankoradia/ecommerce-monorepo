package com.ecommerce.productservice.exceptions;

import lombok.Getter;

@Getter
public class BaseException extends Exception {
    private String details;

    public BaseException(String message, String details) {
        super(message);
        this.details = details;
    }
}
