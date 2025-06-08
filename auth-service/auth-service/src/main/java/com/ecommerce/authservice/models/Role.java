package com.ecommerce.authservice.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Role extends  BaseModel{
    private String name;

    // can add a list of permissions here
}
