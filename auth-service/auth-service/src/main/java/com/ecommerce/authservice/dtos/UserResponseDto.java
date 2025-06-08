package com.ecommerce.authservice.dtos;

import com.ecommerce.authservice.models.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private List<Role> roles = new ArrayList<>();
 }
