package com.ecommerce.productservice.dtos;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AuthenticatedUserDto {
    private Long id;
    private String name;
    private String email;
}
