package com.ecommerce.productservice.controllerAdvice;

import com.ecommerce.productservice.dtos.exceptions.ExceptionDto;
import com.ecommerce.productservice.dtos.exceptions.ProductNotFoundExceptionDto;
import com.ecommerce.productservice.exceptions.CategoryNotFoundException;
import com.ecommerce.productservice.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductServiceExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ProductNotFoundExceptionDto> handleProductNotFoundException(ProductNotFoundException exception) {
        ProductNotFoundExceptionDto exceptionDto = new ProductNotFoundExceptionDto();

        exceptionDto.setProductId(exception.getProductId());
        exceptionDto.setMessage(exception.getMessage());
        exceptionDto.setDetails(exception.getDetails());

        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleCategoryNotFoundException(CategoryNotFoundException exception) {
        ExceptionDto exceptionDto = new ExceptionDto();

        exceptionDto.setMessage(exception.getMessage());
        exceptionDto.setDetails(exception.getDetails());

        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }
}
