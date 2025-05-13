package com.ecommerce.productservice.controllerAdvice;

import com.ecommerce.productservice.dtos.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionDto> handleRuntimeException(RuntimeException runtimeException) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage("Something went wrong");
        exceptionDto.setDetails(runtimeException.getMessage());
        return new ResponseEntity<>(
                exceptionDto,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
